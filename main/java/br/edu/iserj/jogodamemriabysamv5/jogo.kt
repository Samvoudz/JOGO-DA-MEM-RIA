package br.edu.iserj.jogodamemriabysamv5

// Imports Nescessários
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.edu.iserj.jogodamemriabysamv5.databinding.ActivityJogoBinding

// Principal e única classe do programa
class jogo : AppCompatActivity() {

    // Criação da variável binding que conecta a tela ao código kt
    private lateinit var binding: ActivityJogoBinding

    // Criação das variáveis de efeitos sonoros

    // SoundPool para que os efeitos sonoros possam tocar
    private lateinit var soundPool: SoundPool

    // Efeitos sonoros em si
    private var cartaselecionada = 0
    private var acertopar = 0
    private var erropar = 0

    // Música de fundo
    private lateinit var jogomusica: MediaPlayer

    // Criação da lista com as imagens das cartas
    val imagenscartas = listOf(
        R.drawable.mario,R.drawable.mario, R.drawable.luigi, R.drawable.luigi,
        R.drawable.mushroom, R.drawable.mushroom,R.drawable.raccoonmario, R.drawable.raccoonmario,
        R.drawable.raccoonluigi, R.drawable.raccoonluigi, R.drawable.tanooki, R.drawable.tanooki,
        R.drawable.flor, R.drawable.flor, R.drawable.bloco, R.drawable.bloco,
        R.drawable.music,R.drawable.music,R.drawable.estrela,R.drawable.estrela,
    )

    // criação da lista de botões que são as cartas do jogo
    private lateinit var listadecartas: List<Button>

    // variáveis para controle do jogo

    // primeiro botão
    private var primeirobotao: Button? = null
    // segundo botão
    private var segundobotao: Button? = null
    // variável para contagem dos pares
    private var pares = 0

    // Variável de pontuação
    private var pontuacao = 0

    // Criando a lista para assimilar com as cartas
    var cartas = listOf(
        "carta MARIO1","carta MARIO1","LUIGI1","LUIGI1","carta COGUMELO1",
        "carta COGUMELO1","carta MARIORACCON","carta MARIORACOON","carta LUIGIRACCOON","carta LUIGIRACCOON",
        "carta TANOOKI","carta TANOOKI","carta FLOR_FOGO","carta FLOR_FOGO","carta BLOCO",
        "carta BLOCO","carta MUSICA","carta MUSICA","carta ESTRELA","carta ESTRELA")

    override fun onCreate(savedInstanceState: Bundle?) {

        // Configurando a música principal
        jogomusica = MediaPlayer.create(this, R.raw.jogomusica)
        // Colocando a música em loop
        jogomusica.isLooping = true
        // Começando a tocar a música
        jogomusica.start()

        // Configuração de efeitos sonoros
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        // Configuração de SoundPool que vai ser responsável pelos efeitos sonoros tocarem
           soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()

        // Assimilação do SoundPool com as variáveis de som
        cartaselecionada = soundPool.load(this, R.raw.selecionacarta, 1)
        acertopar = soundPool.load(this, R.raw.acertopar, 1)
        erropar = soundPool.load(this, R.raw.erropar, 1)

        binding = ActivityJogoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Criação da lista de botões que serão assimilados as cartas
        listadecartas = listOf(
            binding.buttoncard1,binding.buttoncard2,binding.buttoncard3,binding.buttoncard4,
            binding.buttoncard5,binding.buttoncard6,binding.buttoncard7,binding.buttoncard8,
            binding.buttoncard9,binding.buttoncard10,binding.buttoncard11,binding.buttoncard12,
            binding.buttoncard13,binding.buttoncard14,binding.buttoncard15,binding.buttoncard16,
            binding.buttoncard17,binding.buttoncard18,binding.buttoncard19,binding.buttoncard20)

        // Verifica se o tamanho da lista de botões é igual ao número de cartas
        if (listadecartas.size != imagenscartas.size) {
            throw IllegalStateException("O número de botões e imagens não correspondem")
        }

        // Variável que embaralha a posição das cartas
        val shuffledCards = imagenscartas.shuffled()

        // Configuração do embaralhamento
        listadecartas.forEachIndexed { index, button ->
            button.tag = shuffledCards[index]
            button.setOnClickListener { onCardClicked(button) }
        }
    }

    // Função do clique na carta
    private fun onCardClicked(button: Button) {

        // Se clicou em uma carta
        if (primeirobotao != null && segundobotao != null) return

        // Toca o efeito sonoro
        soundPool.play(cartaselecionada, 1f, 1f, 0, 0, 1f)

        // Trocando a imagem de fundo pela imagem da carta
        val cardImageResId = button.tag as? Int ?: return
        button.setBackgroundResource(cardImageResId)

        // Transformando a variável em um botão
        if (primeirobotao == null) {
            primeirobotao = button
            button.isEnabled = false
        } else {
            segundobotao = button
            button.isEnabled = false

            // Chamando a função para verificar se os pares são iguais
            checarpares()
        }
    }

    // Função que verifica se os pares são iguais
    private fun checarpares() {
        val handler = Handler()
        // Se os pares forem iguais
        if (primeirobotao?.tag == segundobotao?.tag) {
            // + 200 pontos para o jogador
            pontuacao += 200
            // Contador de pares sobe (máx 10)
            pares++
            // Toca o efeito sonoro
            soundPool.play(acertopar, 1f, 1f, 0,0,1f)
            // Torna o botão 1 clicavel de novo
            primeirobotao = null
            // Torna o botão 2 clicavel de novo
            segundobotao = null

            // Exibe os pontos
            binding.texpontos.setText("$pontuacao")

            // Verifica se todos os pares já foram encontrados
            if (pares == imagenscartas.size / 2) {

                // Jogo terminou com vitória do player

                // Música para de tocar
                jogomusica.stop()

                // Finaliza-se o jogo.kt e começa a tela do vencedor
                val intent = Intent(this, telavencedor::class.java)

                // Manda o valor da pontuação para ser exibido depois
                intent.putExtra("PontosF", pontuacao)
                startActivity(intent)
                finish()
            }

            // Se os pares não forem iguais
        } else {

            // -20 pontos
            pontuacao -=20
            handler.postDelayed({

                // Toca o efeito sonoro
                soundPool.play(erropar,1f,1f,0,0,1f)

                // Troca a imagem da carta pela imagem de fundo
                primeirobotao?.setBackgroundResource(R.drawable.cogumelo)
                primeirobotao?.isEnabled = true
                segundobotao?.setBackgroundResource(R.drawable.cogumelo)
                segundobotao?.isEnabled = true

                // Torna os botões clicaveis de novo
                primeirobotao = null
                segundobotao = null

                // Pequeno delay para vizualização das cartas
            }, 750)
        }

        // Jogo termina com derrota
        if (pontuacao == -300) {

            // Música para de tocar
            jogomusica.stop()

            // Encerra o jogo.kt e começa a tela de game over
            val intent = Intent(this, gameover::class.java)
            intent.putExtra("PontosF", pontuacao)
            startActivity(intent)
            finish()
        }

        // Chamando a função que atualiza a pontuação na tela
        pontuacaoatualizar()
    }

    // Função que atualiza a pontuação na tela
    fun pontuacaoatualizar(){

        // Atualizando a pontuação
        binding.texpontos.text = "$pontuacao"
    }
}