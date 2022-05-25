package com.incrowdsports.task.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.incrowdsports.task.R
import com.incrowdsports.task.model.data.Fixture
import com.incrowdsports.task.databinding.FixtureListFragmentBinding
import com.incrowdsports.task.ui.detail.FixtureDetailFragment
import com.incrowdsports.task.util.Resource
import com.incrowdsports.task.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FixtureListFragment : Fragment(R.layout.fixture_list_fragment) {

    private val binding by lazy { FixtureListFragmentBinding.bind(requireView()) }
    private val viewModel: FixtureListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch { viewModel.loadData() }
        }

        val adapter = FixtureListAdapter(::onItemClicked)
        binding.recyclerView.adapter = adapter

        viewModel.fixtureList
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.swipeRefreshLayout.isRefreshing = false
                when(it) {
                    is Resource.Success -> {
                        it.data?.let { items ->
                            adapter.submitList(items)
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let { msg -> showToast(msg) }
                    }
                }
            }
            .launchIn(lifecycleScope)

        lifecycleScope.launch { viewModel.loadData() }
    }

    private fun onItemClicked(item: Fixture) {
        activity?.supportFragmentManager?.commit {
            add(R.id.fragmentContainerView, FixtureDetailFragment.newInstance(item.feedMatchId.toString()))
            addToBackStack(null)
        }
    }

    companion object {
        fun newInstance() = FixtureListFragment()
    }
}