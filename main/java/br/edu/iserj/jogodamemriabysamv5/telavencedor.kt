package br.edu.iserj.jogodamemriabysamv5

// Imports que serão usados
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.iserj.jogodamemriabysamv5.databinding.ActivityTelavencedorBinding

// Criação da única e principal classe da Tela de Vencedor
class telavencedor : AppCompatActivity() {

    // Variáveis de efeitos sonoros
    private var botaoclique = 0
    // Variável soundPool
    private lateinit var soundPool: SoundPool
    // Variável da música de vitória
    private lateinit var musicavitoria: MediaPlayer

    // Criação da variável binding que liga a tela com o código fonte
    private lateinit var binding: ActivityTelavencedorBinding

    // Ligação da tela com o código
    override fun onCreate(savedInstanceState: Bundle?) {

        // Configurações da música de vitória
        musicavitoria = MediaPlayer.create(this, R.raw.telavencedor)
        musicavitoria.start()

        binding = ActivityTelavencedorBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
        botaoclique= soundPool.load(this, R.raw.botaoclick, 1)


        // Criação de uma variável que recebe outra variável vinda do Jogo.kt para exibir a pontuação
        val PontosF = intent.getIntExtra("PontosF", 0)

        // Exibição desse valor de pontuação pego do Jogo.kt
        binding.textopontuacao.text = "Pontuação Final: $PontosF" + " Pontos"

        // Criação de uma variável que assimila o ID de um botão no XML com o código para retornar à MainActivity
        var voltar = binding.buttonvoltar

        // Voltando à MainActivity
        voltar.setOnClickListener {

            // Parando a música de vitória
            musicavitoria.stop()
            // Tocando o efeito sonoro do botão
            soundPool.play(botaoclique, 1f, 1f, 0, 0, 1f)

            // Encerrando essa atividade e voltado para a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}