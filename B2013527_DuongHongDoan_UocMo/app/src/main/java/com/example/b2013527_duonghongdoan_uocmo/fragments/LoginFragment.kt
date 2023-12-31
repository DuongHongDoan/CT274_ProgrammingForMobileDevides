package com.example.b2013527_duonghongdoan_uocmo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.b2013527_duonghongdoan_uocmo.R
import com.example.b2013527_duonghongdoan_uocmo.apis.Constants
import com.example.b2013527_duonghongdoan_uocmo.databinding.FragmentLoginBinding
import com.example.b2013527_duonghongdoan_uocmo.models.RequestRegisterOrLogin
import com.example.b2013527_duonghongdoan_uocmo.sharedpreferences.AppSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        //khoi tao mAppSharedPreferences
        mAppSharedPreferences = AppSharedPreferences(requireContext())

        binding.apply {
            tvRegister.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, RegisterFragment())
                    .commit()
            }

            btnLogin.setOnClickListener {
                if(edtUsername.text.isNotEmpty()) {
                    username = edtUsername.text.toString().trim()
                    //call api dang nhap tai khoan
                    loginUser(username)
                }
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loginUser(username: String) {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {//luong duoc chia nho
                withContext(Dispatchers.Main) {//tuong tac voi giao dien cua minh duoc
                    val resp = Constants.getInstance().loginUser(RequestRegisterOrLogin(username))
                        .body()
                    if(resp != null) {
                        if(resp.success) {
                            //dang nhap thanh cong
                            //nhan idUser va luu vao sharedPreferences
                            mAppSharedPreferences.putIdUser("idUser", resp.idUser!!)
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, WishListFragment())
                                .commit()
                            progressBar.visibility = View.GONE
                        }
                        else {
                            //dang nhap that bai
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