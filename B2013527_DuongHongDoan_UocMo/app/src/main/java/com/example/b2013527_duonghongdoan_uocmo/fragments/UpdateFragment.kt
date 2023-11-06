package com.example.b2013527_duonghongdoan_uocmo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.b2013527_duonghongdoan_uocmo.R
import com.example.b2013527_duonghongdoan_uocmo.apis.Constants
import com.example.b2013527_duonghongdoan_uocmo.databinding.FragmentAddBinding
import com.example.b2013527_duonghongdoan_uocmo.databinding.FragmentUpdateBinding
import com.example.b2013527_duonghongdoan_uocmo.models.RequestAddWish
import com.example.b2013527_duonghongdoan_uocmo.models.RequestUpdateWish
import com.example.b2013527_duonghongdoan_uocmo.sharedpreferences.AppSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var idUser = ""
    private var idWish = ""
    private var fullName = ""
    private var content = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)

        //khoi tao mAppSharedPreferences va lay idUSer tu do ra
        mAppSharedPreferences = AppSharedPreferences(requireContext())
        idUser = mAppSharedPreferences.getIdUser("idUser").toString()
        idWish = mAppSharedPreferences.getIdUser("idWish").toString()
        fullName = mAppSharedPreferences.getIdUser("fullName").toString()
        content = mAppSharedPreferences.getIdUser("content").toString()

        binding.edtContent.setText(content)
        binding.edtFullName.setText(fullName)

        binding.apply {
            btnSave.setOnClickListener {
                if(edtFullName.text.isNotEmpty() && edtContent.text.isNotEmpty()) {
                    fullName = edtFullName.text.toString().trim()
                    content = edtContent.text.toString().trim()
                    //call api them dieu uoc
                    updateWish(fullName, content)
                }
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateWish(fullName: String, content: String) {
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val resp = Constants.getInstance()
                    .updateWish(RequestUpdateWish(idUser, idWish, fullName, content))
                    .body()
                if(resp != null) {
                    if(resp.success) {
                        binding.progressBar.visibility = View.GONE
                        //cap nhat dieu uoc thanh cong
                        Toast.makeText(requireContext(), resp.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, WishListFragment())
                            .commit()
                    }
                    else {
                        binding.progressBar.visibility = View.GONE
                        //cap nhat dieu uoc khong thanh cong
                        Toast.makeText(requireContext(), resp.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, LoginFragment())
                            .commit()
                    }
                }
            }
        }
    }
}