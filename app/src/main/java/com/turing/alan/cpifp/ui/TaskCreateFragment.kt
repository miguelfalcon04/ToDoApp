package com.turing.alan.cpifp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.turing.alan.cpifp.R
import com.turing.alan.cpifp.data.InMemoryTaskRepository
import com.turing.alan.cpifp.data.Task
import com.turing.alan.cpifp.data.TaskRepository
import com.turing.alan.cpifp.databinding.FragmentTaskCreateBinding

class TaskCreateFragment : Fragment() {

    private val repository: TaskRepository = InMemoryTaskRepository.getInstance()
    private lateinit var binding: FragmentTaskCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskCreateBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveTaskButton.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val body = binding.bodyInput.text.toString()
            repository.create(title, body)
            findNavController().popBackStack()

        }
    }

}