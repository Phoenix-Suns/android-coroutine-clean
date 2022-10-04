package self.tranluunghia.mvicoroutine.core.basemvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import self.tranluunghia.mvicoroutine.R
import self.tranluunghia.mvicoroutine.core.helper.DialogHelper
import self.tranluunghia.mvicoroutine.core.helper.extention.observe

/**
Pros
State objects are immutable so it is thread safe.
All actions like state, event, effect is in same file so it is easy to understand what happens in the screen at one look.
Maintaining state is easy.
Since data flow is unidirectional, tracking is easy.

Cons
It causes a lot of boilerplate.
High memory management because we have to create lots of object.
Sometimes, we have many views and complicated logics, in this kind of situation State become huge and we might want split this State into smaller ones with extra StateFlows instead of just using one.

https://github.com/orbit-mvi/orbit-mvi
 */
//abstract class BaseMVIFragment<VIEW_MODEL: BaseMVIViewModel<*, STATE>, DATA_BINDING: ViewBinding, STATE: BaseMVIContract.BaseState>: Fragment() {
abstract class BaseMVIFragment<VIEW_MODEL : BaseMVIViewModel<*, *, *>, DATA_BINDING : ViewBinding> :
    Fragment() {
    protected lateinit var binding: DATA_BINDING
    lateinit var viewModel: VIEW_MODEL
    abstract fun layout(): Int
    abstract fun viewModelClass(): Class<VIEW_MODEL>

    private val loadingDialog by lazy { DialogHelper.createLoadingDlg(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[viewModelClass()]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setEvents()

        subscribeUI()
    }

    open fun setUpViews() {}

    open fun setEvents() {}

    open fun subscribeUI() {
        // Loading
        observe(viewModel.loadingEvent) { isLoading ->
            if (isLoading == true) {
                loadingDialog.show()
            } else {
                loadingDialog.hide()
            }
        }

        // Handle some error
        observe(viewModel.errorMessageEvent) { errorMessage ->
            DialogHelper.createAlertDlg(
                requireContext(),
                title = getString(R.string.error),
                message = errorMessage,
                cancellable = true,
                onClose = null,
            ).show()
        }
        observe(viewModel.noInternetConnectionEvent) {
            DialogHelper.createAlertDlg(
                requireContext(),
                title = getString(R.string.error),
                message = getString(R.string.no_internet_connection),
                cancellable = true,
                onClose = null,
            ).show()
        }
        observe(viewModel.connectTimeoutEvent) {
            DialogHelper.createAlertDlg(
                requireContext(),
                title = getString(R.string.error),
                message = getString(R.string.unknown_error),
                cancellable = true,
                onClose = null,
            ).show()
        }
        observe(viewModel.tokenExpiredEvent) {
            DialogHelper.createAlertDlg(
                requireContext(),
                title = getString(R.string.error),
                message = getString(R.string.you_need_login_again),
                cancellable = true,
                onClose = null,
            ).show()
        }
    }

}
