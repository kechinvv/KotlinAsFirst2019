@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.IllegalArgumentException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val month = mapOf(
    "января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4,
    "мая" to 5, "июня" to 6, "июля" to 7, "августа" to 8,
    "сентября" to 9, "октября" to 10, "ноября" to 11, "декабря" to 12
)

fun dateStrToDigit(str: String): String {
    val time = str.split(" ")
    return if (time.size == 3 && time[1] in month && "[0-9]+".toRegex().matches(time[0]) && "[0-9]+".toRegex().matches(
            time[2]
        )
        && time[0].toInt() in 1..daysInMonth(month[time[1]] ?: error(""), time[2].toInt())
    )
        String.format("%02d.%02d.%d", time[0].toInt(), month[time[1]], time[2].toInt())
    else ""
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
val month1 = mapOf(
    1 to "января", 2 to "февраля", 3 to "марта", 4 to "апреля",
    5 to "мая", 6 to "июня", 7 to "июля", 8 to "августа",
    9 to "сентября", 10 to "октября", 11 to "ноября", 12 to "декабря"
)

fun dateDigitToStr(digital: String): String {
    val time = digital.split(".")
    return if (time.size == 3 && "[0-9]+".toRegex().matches(time[0]) && "[0-9]+".toRegex().matches(time[2]) &&
        time[1].toInt() in month1 && time[0].toInt() <= daysInMonth(time[1].toInt(), time[2].toInt())
    )
        String.format("%d %s %d", time[0].toInt(), month1[time[1].toInt()] ?: error(""), time[2].toInt())
    else ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    return if ("^\\+?[0-9 -]*(\\([0-9 -]*\\d+[0-9 -]*\\))?[0-9 -]*".toRegex().matches(phone) && "\\d".toRegex().find(
            phone
        ) != null
    ) "[- ()]".toRegex().replace(
        phone,
        ""
    )
    else ""
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    return if ("^[0-9]+(( [-%])* [0-9]+)*".toRegex().matches(jumps)) {
        val format = "[- %]+".toRegex().replace(jumps, " ")
        val list = format.split(" ").map { it.toInt() }
        list.maxBy { it }!!
    } else -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    return if ("^([0-9]+ [+%-]+)( [0-9]+ [+%-]+)*".toRegex().matches(jumps)) {
        val a = "([0-9]+ \\+)".toRegex().findAll(jumps)
        a.map { it.value.replace(" +", "").toInt() }.max() ?: -1
    } else -1
}


/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (!"^([0-9]+)( [+-] [0-9]+)*".toRegex().matches(expression)) throw IllegalArgumentException()
    var str = "( \\+)".toRegex().replace(expression, "")
    str = "(- )".toRegex().replace(str, "-")
    val res = str.split(" ")
    return res.sumBy { it.toInt() }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val a = "([^ ]+) \\1".toRegex().find(str.toLowerCase())
    return a?.range?.first ?: -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if ((description == "") || ("\\S*\\s\\d+((\\.\\d+)|)(; |)".toRegex().replace(description, "") != "")) return ""
    val form = "(; )".toRegex().split(description)
    val res = mutableListOf<Pair<Double, String>>()
    for (i in form.indices)
        res.add(form[i].split(" ")[1].toDouble() to form[i].split(" ")[0])
    return (res.maxBy { it.first }!!.second)
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (("[^IVXLCDM]".toRegex().find(roman) != null) || (roman == "")) return -1
    var res = 0
    var n = -1
    val map = mapOf(
        "I" to 1, "IV" to 4, "V" to 5, "IX" to 9, "X" to 10, "XL" to 40,
        "L" to 50, "XC" to 90, "C" to 100, "CD" to 400, "D" to 500, "CM" to 900, "M" to 1000
    )
    while (n != roman.length - 1) {
        n++
        if ((n + 1 <= roman.length - 1) && (map[roman[n].toString()] ?: -1 < map[roman[n + 1].toString()] ?: -1)) {
            if ((roman[n].toString() + roman[n + 1]) in map) {
                res += map[roman[n].toString() + roman[n + 1]] ?: -1
                n++
            }
        } else
            res += map[roman[n].toString()] ?: -1
    }
    return res
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    if ("[+-<>\\[\\] ]".toRegex().replace(commands, "") != "")
        throw IllegalArgumentException()
    val check = "[\\[\\]]".toRegex().findAll(commands).joinToString(separator = "") { it.value }
    var k = 0
    for (i in check.indices) {
        if ((k == 0) && (check[i] == ']')) {
            throw IllegalArgumentException()
        }
        if (check[i] == '[') k++ else k--
    }
    if (k != 0) throw IllegalArgumentException()
    var res = mutableListOf<Int>()
    for (i in 0 until cells) res.add(0)
    var n = cells / 2
    var i = 0
    var counter2 = 0
    fun circle(
        counter: Int,
        res1: MutableList<Int>,
        j1: String,
        n1: Int,
        i1: Int
    ): Triple<Int, Int, MutableList<Int>> {
        var ii = i1
        var nn = n1
        var f = 0
        var ress = res1
        var count = counter + 1
        while (ii != limit) {
            ii++
            when {
                j1[count] == '[' -> {
                    if (ress[nn] != 0) {
                        val gg = circle(count, ress, j1, nn, ii)
                        ress = gg.third
                        ii = gg.first
                        nn = gg.second
                    }
                    k = 1
                    do {
                        count++
                        if (j1[count] == ']') k--
                        if (j1[count] == '[') k++
                    } while (k != 0)
                }
                j1[count] == ']' ->
                    if (ress[nn] != 0) {
                        k = -1
                        do {
                            count--
                            if (j1[count] == ']') k--
                            if (j1[count] == '[') k++
                        } while (k != 0)
                    } else f = 1
                j1[count] == '<' -> nn--
                j1[count] == '>' -> nn++
                j1[count] == '+' -> ress[nn] = ress[nn] + 1
                j1[count] == '-' -> ress[nn] = ress[nn] - 1
            }
            if ((nn > cells - 1) or (nn < 0)) throw IllegalStateException()
            count++
            if (f == 1) break
        }
        return (Triple(ii, nn, ress))
    }
    while ((i != limit) && (counter2 != commands.length)) {
        i++
        when {
            commands[counter2] == '[' -> {
                if (res[n] != 0) {
                    val cir = circle(counter2, res, commands, n, i)
                    res = cir.third
                    i = cir.first
                    n = cir.second
                }
                do {
                    if (commands[counter2] == '[') k++
                    counter2++
                    if (commands[counter2] == ']') k--
                } while (k != 0)
            }
            commands[counter2] == '<' -> n--
            commands[counter2] == '>' -> n++
            commands[counter2] == '+' -> res[n]++
            commands[counter2] == '-' -> res[n]--
        }
        if ((n > cells - 1) || (n < 0)) throw IllegalStateException()
        counter2++
        k = 0
    }
    return res
}