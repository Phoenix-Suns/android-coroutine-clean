package self.tranluunghia.mvicoroutine.presentation.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import self.tranluunghia.mvicoroutine.R
import self.tranluunghia.mvicoroutine.presentation.feature.randomnumber.RandomNumberFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, RandomNumberFragment(), null).commit()
    }
}