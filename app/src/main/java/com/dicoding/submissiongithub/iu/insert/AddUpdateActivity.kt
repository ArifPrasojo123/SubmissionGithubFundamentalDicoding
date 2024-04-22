package com.dicoding.submissiongithub.iu.insert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissiongithub.R
import com.dicoding.submissiongithub.database.FavoriteUser
import com.dicoding.submissiongithub.databinding.ActivityAddUpdateBinding
import com.dicoding.submissiongithub.helper.DateHelper
import com.dicoding.submissiongithub.iu.main.ViewModelFactory

class AddUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FAVORITEUSER = "extra_favoriteuser"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var favoriteUser: FavoriteUser? = null

    private lateinit var AddUpdateViewModel: AddUpdateViewModel

    private var _activityAddUpdateBinding: ActivityAddUpdateBinding? = null
    private val binding get() = _activityAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityAddUpdateBinding = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        AddUpdateViewModel = obtainViewModel(this@AddUpdateActivity)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
        })

        favoriteUser = intent.getParcelableExtra(EXTRA_FAVORITEUSER)
        if (favoriteUser != null) {
            isEdit = true
        } else {
            favoriteUser = FavoriteUser()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (favoriteUser != null) {
                favoriteUser?.let { favoriteuser ->
                    binding?.edtTitle?.setText(favoriteuser.title)
                    binding?.edtDescription?.setText(favoriteuser.description)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.btnsubmit?.text = btnTitle

        binding?.btnsubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDescription?.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding?.edtTitle?.error = getString(R.string.empty)
                }
                description.isEmpty() -> {
                    binding?.edtDescription?.error = getString(R.string.empty)
                }
                else -> {
                    favoriteUser.let { favoriteuser ->
                        favoriteUser?.title = title
                        favoriteUser?.description = description
                    }
                    if (isEdit) {
                        AddUpdateViewModel.update(favoriteUser as FavoriteUser)
                        showToast(getString(R.string.changed))
                    } else {
                        favoriteUser.let { note ->
                            favoriteUser?.date = DateHelper.getCurrentDate()
                        }
                        AddUpdateViewModel.insert(favoriteUser as FavoriteUser)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (isDialogClose) {
                    AddUpdateViewModel.delete(favoriteUser as FavoriteUser)
                    showToast(getString(R.string.deleted))
                }

                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _activityAddUpdateBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): AddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddUpdateViewModel::class.java)
    }
}