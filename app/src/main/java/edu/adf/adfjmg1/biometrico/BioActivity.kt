package edu.adf.adfjmg1.biometrico

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R

class BioActivity : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bio)

//        checkBiometricAvailability()
        // Llama al método para iniciar autenticación biométrica
        showBiometricPrompt()
    }

    private fun showBiometricPrompt() {
        val biometricManager = BiometricManager.from(this)

        // Verifica si el dispositivo tiene biometría configurada
        when (biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_WEAK or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                initBiometricPrompt()
                biometricPrompt.authenticate(promptInfo)
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Fallback si no hay biometría
                fallbackToDeviceCredential()
            }
        }
    }

    private fun initBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "Autenticación correcta", Toast.LENGTH_SHORT).show()
                    // Aquí pones lo que debe pasar tras autenticación
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Error: $errString", Toast.LENGTH_SHORT).show()

                    // Si se bloquea el sensor por demasiados intentos fallidos
                    if (errorCode == BiometricPrompt.ERROR_LOCKOUT ||
                        errorCode == BiometricPrompt.ERROR_LOCKOUT_PERMANENT) {
                        fallbackToDeviceCredential()
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Huella incorrecta", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = PromptInfo.Builder()
            .setTitle("Autenticación requerida")
            .setSubtitle("Usa tu huella o el PIN del sistema")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_WEAK or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()
    }

    // Fallback para versiones < Android 10
    private fun fallbackToDeviceCredential() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if (keyguardManager.isKeyguardSecure) {
                val intent = keyguardManager.createConfirmDeviceCredentialIntent(
                    "Autenticación requerida",
                    "Confirma tu PIN, patrón o contraseña"
                )
                launchDeviceCredentialPrompt.launch(intent)
            } else {
                Toast.makeText(this, "No hay PIN o patrón configurado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Registro del resultado del intent de autenticación con PIN/patrón
    private val launchDeviceCredentialPrompt = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "Autenticación correcta (PIN)", Toast.LENGTH_SHORT).show()
            // Aquí haces lo que necesites tras autenticación
        } else {
            Toast.makeText(this, "Autenticación cancelada", Toast.LENGTH_SHORT).show()
        }
    }


//    private fun checkBiometricAvailability() {
//
//        val biometricManager = BiometricManager.from(this)
//
//        when (biometricManager.canAuthenticate()) {
//            BiometricManager.BIOMETRIC_SUCCESS -> {
//                // El dispositivo tiene biometría (huella, rostro, etc.) y está configurada
//                Log.d(Constantes.ETIQUETA_LOG, "Biometría disponible y lista para usar")
//                showBiometricPrompt()
//            }
//            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
//                // El dispositivo no tiene hardware biométrico
//                Log.d(Constantes.ETIQUETA_LOG, "No hay hardware biométrico disponible")
//            }
//            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
//                // El hardware biométrico está actualmente no disponible
//                Log.d(Constantes.ETIQUETA_LOG, "Hardware biométrico no disponible temporalmente")
//            }
//            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
//                // El dispositivo tiene hardware biométrico, pero no hay huellas o biometría registrada
//                Log.d(Constantes.ETIQUETA_LOG, "No hay datos biométricos registrados")
//            }
//            else -> {
//                Log.d(Constantes.ETIQUETA_LOG, "Estado biométrico desconocido")
//            }
//        }
//    }
//
//    // Dentro de tu Activity o Fragment
//
//    fun showBiometricPrompt() {
//
//        val executor = ContextCompat.getMainExecutor(this)
//
//        val biometricPrompt = BiometricPrompt(this, executor,
//            object : BiometricPrompt.AuthenticationCallback() {
//                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                    super.onAuthenticationSucceeded(result)
//                    runOnUiThread {
//                        // Aquí pones lo que quieres hacer al autenticarse correctamente
//                        Toast.makeText(this@BioActivity, "Autenticación correcta", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onAuthenticationFailed() {
//                    super.onAuthenticationFailed()
//                    runOnUiThread {
//                        Toast.makeText(this@BioActivity, "Autenticación fallida", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                    super.onAuthenticationError(errorCode, errString)
//                    runOnUiThread {
//                        Toast.makeText(this@BioActivity, "Error: $errString", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })
//
//        val promptInfo = BiometricPrompt.PromptInfo.Builder()
//            .setTitle("Inicia sesión con huella")
//            .setSubtitle("Usa tu huella para iniciar sesión")
//            .setNegativeButtonText("Cancelar")
//            .build()
//
//        biometricPrompt.authenticate(promptInfo)
//
//
//    }
}