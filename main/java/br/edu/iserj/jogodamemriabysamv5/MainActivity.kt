package br.edu.iserj.jogodamemriabysamv5

// Imports para o projeto
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.iserj.jogodamemriabysamv5.databinding.ActivityMainBinding

// Principal e Única classe da tela principal
class MainActivity : AppCompatActivity() {

    // Variável Binding que permite que a tela interaja com o código
    private lateinit var binding: ActivityMainBinding

    // Variáveis para efeitos sonoros
    private lateinit var soundPool: SoundPool
    private var botaoclick = 0
    // Variável para a música de fundo
    private lateinit var musicaprincipal: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {

        // Configura a música para tocar de fundo
        musicaprincipal = MediaPlayer.create(this, R.raw.telainicial)
        // Coloca a música em Loop
        musicaprincipal.isLooping = true
        // Começa a tocar a música
        musicaprincipal.start()

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
        botaoclick = soundPool.load(this, R.raw.botaoclick, 1)

        // Assimilação de Binding com a tela ActivityMain
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Criação de uma variável que é assimilada com um botão no XML
        var jogar = binding.buttonjogar

        // Função que fecha a MainActivity e começa a tela que contém o jogo em si
        jogar.setOnClickListener {

            // Comando para parar a música principal
            musicaprincipal.stop()

            // Comando para tocar o efeito sonoro quando o botão é clickado
            soundPool.play(botaoclick, 1f, 1f, 0, 0, 1f)
            val intent = Intent(this, jogo::class.java)
            startActivity(intent)
            finish()
        }

        // Função que fecha a tela principal e inicia a tela de JukeBox com as músicas do Super Mario Bros 3 de SNES
        var musicas = binding.buttonjuke

        // Variável para ir para a tela de jukebox
        var musica = binding.buttonjuke

        // Indo para a tela de jukebox
        musicas.setOnClickListener {

            val intent = Intent(this, Jukebox::class.java)
            startActivity(intent)
            finish()
        }
    }
}