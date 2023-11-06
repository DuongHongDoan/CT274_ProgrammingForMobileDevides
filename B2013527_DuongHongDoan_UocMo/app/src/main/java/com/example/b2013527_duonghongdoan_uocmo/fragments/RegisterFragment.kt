package com.example.b2013527_duonghongdoan_uocmo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.b2013527_duonghongdoan_uocmo.R
import com.example.b2013527_duonghongdoan_uocmo.apis.Constants
import com.example.b2013527_duonghongdoan_uocmo.databinding.FragmentLoginBinding
import com.example.b2013527_duonghongdoan_uocmo.databinding.FragmentRegisterBinding
import com.example.b2013527_duonghongdoan_uocmo.models.RequestRegisterOrLogin
import com.example.b2013527_duonghongdoan_uocmo.sharedpreferences.AppSharedPreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        //khoi tao mAppSharedPreferences
        mAppSharedPreferences = AppSharedPreferences(requireContext())

        binding.apply {
            btnRegister.setOnClickListener {
                if(edtUsername.text.isNotEmpty()) {
                    username = edtUsername.text.toString().trim()
                    //call api dang ky tai khoan
                    registerUser(username)
                }
                else {
                    Snackbar.make(it, "Vui lòng nhập mã số sinh viên", Snackbar.LENGTH_LONG).show()
                }
            }

            tvLogin.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, LoginFragment())
                    .commit()
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun registerUser(username: String) {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    val resp = Constants.getInstance().registerUser(RequestRegisterOrLogin(username))
                        .body()
                    if(resp != null) {
                        if(resp.success) {
                            progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), resp.idUser, Toast.LENGTH_SHORT).show()
                            //dang ky thanh cong
                            //nhan idUser va luu vao sharedPreferences
                            mAppSharedPreferences.putIdUser("idUser", resp.idUser!!)
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, WishListFragment())
                                .commit()
                        }
                        else {
                            //dang ky that bai
                            tvMessage.text = resp.message
                            tvMessage.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}