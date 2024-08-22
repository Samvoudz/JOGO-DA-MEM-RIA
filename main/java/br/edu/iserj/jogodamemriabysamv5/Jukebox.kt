
// EM BREVE
package br.edu.iserj.jogodamemriabysamv5

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.iserj.jogodamemriabysamv5.databinding.ActivityJukeboxBinding

class Jukebox : AppCompatActivity() {

    private lateinit var binding: ActivityJukeboxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJukeboxBinding.inflate(layoutInflater)
        setContentView(binding.root)
       val voltar = binding.buttonvoltar
        voltar.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}