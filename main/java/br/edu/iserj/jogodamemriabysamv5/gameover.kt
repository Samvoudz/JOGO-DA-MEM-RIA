package br.edu.iserj.jogodamemriabysamv5

// Imports Nescessários
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.iserj.jogodamemriabysamv5.databinding.ActivityGameoverBinding

// Principal e única classe do programa
class gameover : AppCompatActivity() {

    //Criação da variável binding que liga a tela ao código
    private lateinit var binding: ActivityGameoverBinding

    // Criação das variáveis de som
    private var botaoclique = 0
    private lateinit var soundPool: SoundPool
    private lateinit var musicaderrota: MediaPlayer

    // Assimilação da Tela com o código
    override fun onCreate(savedInstanceState: Bundle?) {

        // Configura a música para tocar de fundo
        musicaderrota = MediaPlayer.create(this, R.raw.gameover)
        // Começa a tocar a música
        musicaderrota.start()

        // Configurações dos efeitos de som para que eles possam serem usados
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        // Configuração da variável soundPool
        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()

        // Atribuição de feito sonoro ao arquivo R.raw.botaoclick
        botaoclique = soundPool.load(this, R.raw.botaoclick, 1)

        binding = ActivityGameoverBinding.inflate((layoutInflater))
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val PontosF = intent.getIntExtra("PontosF", 0)

        binding.textopontuacao.text = "Pontuação Final: $PontosF" + " Pontos"

        var voltar = binding.buttonvoltar

        // Voltando para MainActivity
        binding.buttonvoltar.setOnClickListener {

            // Parando a música
            musicaderrota.stop()
            // Tocando o som
            soundPool.play(botaoclique, 1f, 1f, 0, 0, 1f)

            // Encerrando essa atividade e voltando para a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}