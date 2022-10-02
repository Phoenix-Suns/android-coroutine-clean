package self.tranluunghia.mvicoroutine.presentation.feature.repolist

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancelChildren
import self.tranluunghia.mvicoroutine.R
import self.tranluunghia.mvicoroutine.core.basemvi.BaseMVIFragment
import self.tranluunghia.mvicoroutine.core.helper.extention.logE
import self.tranluunghia.mvicoroutine.core.helper.extention.singleClick
import self.tranluunghia.mvicoroutine.databinding.FragmentRepoListBinding
import self.tranluunghia.mvicoroutine.presentation.feature.adapter.RepoListAdapter

@AndroidEntryPoint
class RepoListFragment : BaseMVIFragment<RepoListViewModel, FragmentRepoListBinding>() {

    companion object {
        fun newInstance() = RepoListFragment()
    }

    override fun layout(): Int = R.layout.fragment_repo_list
    override fun viewModelClass(): Class<RepoListViewModel> = RepoListViewModel::class.java

    val repoListAdapter by lazy { RepoListAdapter(ArrayList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setUpViews() {
        super.setUpViews()
        binding.recyclerViewRepo.adapter = repoListAdapter
    }

    override fun setEvents() {
        super.setEvents()

        binding.buttonSearch.singleClick {
            val searchKey = binding.editTextSearchKey.text.toString().trim()
            viewModel.sendIntent(RepoListContract.ListIntent.GetList(searchKey))
        }

        binding.buttonDestroyView.singleClick {
            //viewModel.viewModelJob.cancel()
            viewModel.viewModelJob.cancelChildren()
        }
    }

    override fun subscribeUI() {
        super.subscribeUI()

        lifecycleScope.launchWhenCreated {
            viewModel.callbackState.collect {
                handleStates(it)
            }
        }
    }

    private fun handleStates(state: RepoListContract.ListState) {
        when (state) {
            is RepoListContract.ListState.ShowUserInfo -> {
                logE(state.userInfo.name)
            }
            is RepoListContract.ListState.ShowRepoList -> {
                repoListAdapter.updateItems(state.repoList)
            }
        }
    }
}