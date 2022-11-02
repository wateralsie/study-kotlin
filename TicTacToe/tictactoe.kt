import java.lang.Exception

fun main() {
    val board = Array(3) { CharArray(3) { ' ' } }
    val player1 = Player("Player 1", 'O')
    val player2 = Player("Player 2", 'X')

    play(board, player1, player2)
}

// 틱택토 게임 진행
fun play(board: Array<CharArray>, player1: Player, player2: Player) {
    var turn = 1

    while (turn < 10 && !isWin(board, player1, player2)) {
        println("\n${turn}번째 턴\n")
        printBoard(board)

        val result = if (turn % 2 == 1)
            playerInput(board, player1)
        else
            playerInput(board, player2)

        // 유효한 값이 들어왔을 때만 턴 추가
        if (result) turn++
    }

    // 무승부
    if (turn == 10) printBoard(board)
}

// 보드판 출력
fun printBoard(board: Array<CharArray>) {
    // 가로 줄 번호
    print(" ")
    for (i in 0 until 3) print(" $i")
    println()

    // 세로 줄 번호 & 보드판
    for (i in 0 until 3) {
        print("$i ")
        // 세로 격자
        for (j in 0 until 3) {
            print("${board[j][i]}")
            if (j != 2) print("|")
        }
        println()
        // 가로 격자
        if (i != 2) {
            print("  ")
            for (j in 0 until 3) {
                print("-")
                if (j != 2) print("+")
            }
            println()
        }

    }

}

// 격자 범위에 있는 좌표인지 검사
val isInRange: (Int, Int) -> Boolean = { x, y ->
    (x in 0 until 3) && (y in 0 until 3)
}

// 격자 칸이 비어있는지 검사
fun isValid(board: Array<CharArray>, x: Int, y: Int): Boolean = board[y][x] == ' '

// 좌표 입력받고 유효한 값이면 말 배치
fun playerInput(board: Array<CharArray>, player: Player): Boolean {
    val x: Int;
    val y: Int

    try {
        print("${player.name} 입력(줄, 칸): ")
        val input = readLine()!!.split(",").map { it.toInt() }
        y = input[0]
        x = input[1]
    } catch (e: Exception) {
        print(e)
        println()
        return false
    }

    return if (isInRange(x, y) && isValid(board, x, y)) {
        board[y][x] = player.mark
        true
    } else false
}

// 승리한 플레이어 판정
fun isWin(board: Array<CharArray>, player1: Player, player2: Player): Boolean {
    fun isWinner(player: Player): Boolean {
        val mark = player.mark

        // 가로, 세로 방향
        for (i in 0..2) {
            if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark) return true
            if (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark) return true
        }

        // 대각선 방향
        if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark) return true
        if (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark) return true

        return false
    }

    // 우승자 문구 출력
    return when {
        isWinner(player1) -> {
            printBoard(board)
            println("${player1.name} 승리!")
            true
        }

        isWinner(player2) -> {
            printBoard(board)
            println("${player2.name} 승리!")
            true
        }

        else -> {
            false
        }
    }
}