package tech.blur.sexsquare.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import tech.blur.sexsquare.R
import tech.blur.sexsquare.databinding.FragmentStatisticsBinding

class StatisticsFragment: Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentStatisticsBinding>(
        inflater,
        R.layout.fragment_statistics,
        container,
        false
    ).root
}
