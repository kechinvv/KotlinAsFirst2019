@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.isPrime
import lesson5.task1.findCheapestStuff
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.map { it * it }.sum())


/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.size != 0)
    list.sum() / list.size else 0.0

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.size != 0) {
        val a = mean(list)
        for (i in 0 until list.size)
            list[i] -= a
    }
    return list
}


/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var c = 0
    for (i in 0 until a.size) c += a[i] * b[i]
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var s = 0
    var n = 0
    var xs = 1
    for (i in 0 until p.size) {
        s += p[i] * xs
        xs *= x
        n++
    }
    return s
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size) list[i] += list[i - 1]
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val a = mutableListOf<Int>()
    var g = n
    var i = 1
    if (isPrime(g)) a.add(g)
    else while (g != 1) {
        i++
        if (isPrime(i) && (g % i == 0)) {
            a.add(i)
            g /= i
            i = 1
        }
    }
    return a.sorted()
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String =
    factorize(n).joinToString(separator = "*")


/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var f = n
    val out = mutableListOf<Int>()
    do {
        out.add(f % base)
        f /= base
    } while (f != 0)
    return out.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    var f = n
    var c = 0
    var out = ""
    do {
        c = f % base
        if (c < 10)
            out += ("$c") else
            out += (c + 87).toChar()
        f /= base
    } while (f != 0)
    return out.reversed()
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var count = 0
    var s = 0
    for (i in (digits.size - 1) downTo 0) {
        s += digits[i] * (base.toDouble().pow(count).toInt())
        count++
    }
    return s
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    var count = 0
    var s = 0
    for (i in (str.length - 1) downTo 0) {
        s += if ((str[i].toInt() - 48) in 0..9)
            (base.toDouble().pow(count).toInt()) * (str[i].toInt() - 48)
        else
            (base.toDouble().pow(count).toInt()) * (str[i].toInt() - 87)
        count++
    }
    return s
}


/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var a = ""
    val m = n / 1000
    val c = n % 1000 / 100
    val x = n % 100 / 10
    val one = n % 10
    var symbol = "M"
    if (m > 0) a += symbol.repeat(m)
    if (c > 0)
        when {
            c == 9 -> a += "CM"
            c >= 5 -> {
                a += "D"
                symbol = "C"
                a += symbol.repeat(c % 5)
            }
            c == 4 -> a += "CD"
            else -> {
                symbol = "C"
                a += symbol.repeat(c % 5)
            }
        }
    if (x > 0)
        when {
            x == 9 -> a += "XC"
            x == 4 -> a += "XL"
            x >= 5 -> {
                a += "L"
                symbol = "X"
                a += symbol.repeat(x % 5)
            }
            else -> {
                symbol = "X"
                a += symbol.repeat(x % 5)
            }
        }
    if (one > 0)
        when {
            one == 9 -> a += "IX"
            one == 4 -> a += "IV"
            one >= 5 -> {
                a += "V"
                symbol = "I"
                a += symbol.repeat(one % 5)
            }
            else -> {
                symbol = "I"
                a += symbol.repeat(one % 5)
            }
        }
    return a
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    var b = ""
    val a = listOf<String>(
        "",
        "один ", //0
        "два ",  //1
        "три ",  //2
        "четыре ", //3
        "пять ", //4
        "шесть ", //5
        "семь ",  //6
        "восемь ", //7
        "девять ", //8
        "десять ", //9
        "одиннадцать ", //10
        "двенадцать ", //11
        "тринадцать ", //12
        "четырнадцать ", //13
        "пятнадцать ", //14
        "шестнадцать ", //15
        "семнадцать ", //16
        "восемнадцать ", //17
        "девятнадцать ", //18
        "двадцать ", //19
        "тридцать ",  //20
        "сорок ",    //21
        "пятьдесят ",   //22
        "шестьдесят ",  //23
        "семьдесят ",  //24
        "восемьдесят ",  //25
        "девяносто ",  //26
        "сто ",   //27
        "двести ",  //28
        "триста ",  //29
        "четыреста ",  //30
        "пятьсот ",  //31
        "шестьсот ",  //32
        "семьсот ",  //33
        "восемьсот ",  //34
        "девятьсот ", //35
        "", //36
        "одна ",  //37
        "две ", //38
        "три ",  //39
        "четыре ", //40
        "пять ", //41
        "шесть ", //42
        "семь ",  //43
        "восемь ", //44
        "девять " //45
    )
    val thousands = n / 1000
    val hundreds = n % 1000
    if (thousands / 100 != 0) b += a[27 + thousands / 100]
    if (thousands % 100 != 0)
        when {
        thousands % 100 in 1..2 -> b += a[37 + thousands % 100]
        thousands % 100 in 3..19 -> b += a[thousands % 100]
        thousands % 100 in 20..99 -> b += a[18 + thousands % 100 / 10] + a[37 + thousands % 10]
    }
    if (thousands != 0)
        when {
        (thousands % 5 == 0) or (thousands % 10 in 6..9) or (thousands % 100 in 6..19) -> b += "тысяч "
        thousands % 5 == 1 -> b += "тысяча "
        else -> b += "тысячи "
    }
    if (hundreds / 100 != 0) b += a[27 + hundreds / 100]
    if (hundreds % 100 != 0)
        when {
        hundreds % 100 in 1..19 -> b += a[hundreds % 100]
        hundreds % 100 in 20..99 -> b += a[18 + hundreds % 100 / 10] + a[hundreds % 10]
    }
    return b.trim()
}