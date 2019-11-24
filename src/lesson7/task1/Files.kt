@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import ru.spbstu.wheels.tail
import java.io.File
import java.nio.charset.Charset

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val out = mutableMapOf<String, Int>()
    for (i in substrings) out[i] = 0
    var str: String
    var line: String
    for ((i, v) in out)
        for (j in File(inputName).bufferedReader().readLines()) {
            str = j.toLowerCase()
            while (str.contains(i.toLowerCase())) {
                if (str.substring(0, i.length) == i.toLowerCase()) out[i] = out[i]!! + 1
                str = "^.".toRegex().replace(str, "")
            }
        }
    return out
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val out = File(outputName).bufferedWriter()
    val reg = "(?<=[ЖжШшЧчЩщ])"
    for (line in File(inputName).bufferedReader().readLines()) {
        var right = line
        right = "$reg[Ы]".toRegex().replace(right, "И")
        right = "$reg[ы]".toRegex().replace(right, "и")
        right = "$reg[Я]".toRegex().replace(right, "А")
        right = "$reg[я]".toRegex().replace(right, "а")
        right = "$reg[Ю]".toRegex().replace(right, "У")
        right = "$reg[ю]".toRegex().replace(right, "у")
        out.write(
            right
        )
        out.newLine()
    }
    out.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    var max = Int.MIN_VALUE
    for (line in File(inputName).bufferedReader().readLines())
        if (line.trim().length > max) max = line.trim().length
    val out = File(outputName).bufferedWriter()
    for (line in File(inputName).bufferedReader().readLines()) {
        val n = max - line.trim().length
        var space = ""
        space += " ".repeat(n / 2)
        out.write(space + line.trim())
        out.newLine()
    }
    out.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    var max = Int.MIN_VALUE
    var n = 0
    var num = 0
    for (line in File(inputName).bufferedReader().readLines())
        if ("\\s+".toRegex().replace(line.trim(), " ").length > max) max =
            "\\s+".toRegex().replace(line.trim(), " ").length
    val out = File(outputName).bufferedWriter()
    for (line in File(inputName).bufferedReader().readLines()) {
        num = 0
        if (line.isNotBlank()) {
            n = max - "\\s+".toRegex().replace(line.trim(), " ").length
            var words = "\\s+".toRegex().replace(line.trim(), " ").split(" ").map { "${it.trim()} " }
                .toMutableList()
            words[words.size - 1] = words[words.size - 1].trim()
            if (words.size != 1) {
                while (n != 0) {
                    words[num] = words[num] + ' '
                    n--
                    num++
                    if (num == words.size - 1) num = 0
                }
                val str = words.fold("") { a, b -> a + b }
                out.write(str)
            } else
                out.write(words[0].trim())
        }
        out.newLine()
    }
    out.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val out = mutableMapOf<String, Int>()
    for (line in File(inputName).bufferedReader().readLines()) {
        if (line.isNotBlank()) {
            val words = "[^a-zа-яё]+".toRegex().split(line.toLowerCase()).toMutableList()
            for (i in words.indices) if ((out[words[i]] == null) && (words[i].isNotBlank())) out[words[i]] = 1
            else if (words[i].isNotBlank()) out[words[i]] = out[words[i]]!! + 1
        }
    }
    return out.toList().sortedByDescending { it.second }.take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val out = File(outputName).bufferedWriter()
    for (line in File(inputName).bufferedReader().readLines()) {
        val list = line.toList().map { it.toString() }.toMutableList()
        for (i in line.indices)
            when {
                line[i].toUpperCase() in dictionary -> {
                    list[i] = (dictionary[line[i].toUpperCase()] ?: error("")).toLowerCase()
                    if (line[i] == line[i].toUpperCase()) list[i] = list[i].capitalize()
                }
                line[i].toLowerCase() in dictionary -> {
                    list[i] = (dictionary[line[i].toLowerCase()] ?: error("")).toLowerCase()
                    if (line[i] == line[i].toUpperCase()) list[i] = list[i].capitalize()
                }
                else -> list[i] = line[i].toString()
            }
        out.write(list.fold("") { a, b -> a + b })
        out.newLine()
    }
    out.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    var max = Int.MIN_VALUE
    var set = mutableListOf<String>()
    val out = File(outputName).bufferedWriter()
    for (line in File(inputName).bufferedReader().readLines()) when {
        (line.toLowerCase().toCharArray().toSet().size > max &&
                line.toLowerCase().toCharArray().toSet().size == line.length
                ) -> {
            max = line.toLowerCase().toCharArray().toSet().size
            set = mutableListOf(line)
        }
        (line.toLowerCase().toCharArray().toSet().size == max &&
                line.toLowerCase().toCharArray().toSet().size == line.length
                ) -> set.add(line)
    }
    out.write(set.toString().substring(1, set.toString().length - 1))
    out.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val out = File(outputName).bufferedWriter()
    out.write("<html><body>")
    var p = 0
    var b = 0
    var i = 0
    var s = 0
    for (line in File(inputName).bufferedReader().readLines()) {
        val newline = line.toList().map { it.toString() }.toMutableList()
        if (p == 0 && line.isNotBlank()) {
            out.write("<p>")
            p++
        }
        if (p != 0 && line.isBlank()) {
            out.write("</p>")
            p--
        }
        for (k in line.indices) {
            if (newline[k] == "*" && newline[k + 1] == "*") if (b == 0) {
                b++
                newline[k] = "<b>"
                newline[k + 1] = ""
            } else {
                b--
                newline[k] = "</b>"
                newline[k + 1] = ""
            }
            if (newline[k] == "*") if (i == 0) {
                i++
                newline[k] = "<i>"
            } else {
                i--
                newline[k] = "</i>"
            }
            if (newline[k] == "~" && newline[k + 1] == "~") if (s == 0) {
                s++
                newline[k] = "<s>"
                newline[k + 1] = ""
            } else {
                s--
                newline[k] = "</s>"
                newline[k + 1] = ""
            }
        }
        out.write(newline.fold("") { a, b -> a + b })
    }
    if (p != 0) out.write("</p>")
    out.write("</body></html>")
    out.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val list = mutableListOf<String>()
    val out = File(outputName).bufferedWriter()
    out.write("<html><body>")
    var space = -1
    if (File(inputName).bufferedReader().readLine().trim()[0] == '*') {
        out.write(
            "<ul>${"^\\s*(\\* )(.*)".toRegex().replace(
                File(inputName).bufferedReader().readLine(),
                "<li>\$2"
            )}"
        )
        list += "</ul>"
    }
    if ("^\\s*[0-9]+\\..*".toRegex().matches(File(inputName).bufferedReader().readLine())) {
        out.write(
            "<ol>${"^\\s*([0-9]+\\. )(.*)".toRegex().replace(
                File(inputName).bufferedReader().readLine(),
                "<li>\$2"
            )}"
        )
        list += "</ol>"
    }
    space = "^\\s*".toRegex().find(File(inputName).bufferedReader().readLine())!!.value.length
    for (line in File(inputName).bufferedReader().readLines().drop(1)) {
        if (line.isNotBlank()) {
            if ((line.trim()[0] == '*') && (space > "\\s*".toRegex().find(line)!!.value.length)) {
                for (i in list.size - 1 downTo list.size - (space - "\\s*".toRegex().find(line)!!.value.length) / 4) {
                    out.write("</li>${list[i]}")
                    list.removeAt(i)
                }
                out.write("</li>${"^\\s*(\\* )(.*)".toRegex().replace(line, "<li>$2")}")
            }
            if (("^\\s*[0-9]+\\..*".toRegex().matches(line)) && (space > "\\s*".toRegex().find(line)!!.value.length)) {
                for (i in list.size - 1 downTo list.size - (space - "\\s*".toRegex().find(line)!!.value.length) / 4) {
                    out.write("</li>${list[i]}")
                    list.removeAt(i)
                }
                out.write("</li>${"^\\s*([0-9]+\\. )(.*)".toRegex().replace(line, "<li>$2")}")
            }
            if ((line.trim()[0] == '*') && (space < "\\s*".toRegex().find(line)!!.value.length)) {
                out.write("<ul>${"^\\s*(\\* )(.*)".toRegex().replace(line, "<li>$2")}")
                list += "</ul>"
            }
            if (("^\\s*[0-9]+\\..*".toRegex().matches(line)) && (space < "\\s*".toRegex().find(line)!!.value.length)) {
                out.write("<ol>${"^\\s*([0-9]+\\. )(.*)".toRegex().replace(line, "<li>$2")}")
                list += "</ol>"
            }
            if (space == "\\s*".toRegex().find(line)!!.value.length)
                out.write("^\\s*(\\*\\s|[0-9]+\\. )(.*)".toRegex().replace(line, "</li><li>$2"))
            space = "^\\s*".toRegex().find(line)!!.value.length
        }
    }
    for (i in list.size - 1 downTo 0) out.write("</li>${list[i]}")
    out.write("</body></html>")
    out.close()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+  19935
+ 19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val out = File(outputName).bufferedWriter()
    for (i in 0..(rhv * lhv).toString().length - lhv.toString().length) out.write(" ")
    out.write("$lhv")
    out.newLine()
    out.write("*")
    for (i in 1..(rhv * lhv).toString().length - rhv.toString().length) out.write(" ")
    out.write("$rhv")
    out.newLine()
    for (i in 0..(lhv * rhv).toString().length) out.write("-")
    out.newLine()
    for (i in 0..(lhv * rhv).toString().length - (lhv * (rhv % 10)).toString().length) out.write(" ")
    out.write("${lhv * (rhv % 10)}")
    out.newLine()
    if (rhv > 10) {
        var space = 1
        var a = rhv / 10
        while (a != 0) {
            out.write("+")
            for (i in 1..(lhv * rhv).toString().length - (a % 10 * lhv).toString().length - space) out.write(
                " "
            )
            out.write("${(a % 10) * lhv}")
            a /= 10
            space++
            out.newLine()
        }
    }
    for (i in 0..(lhv * rhv).toString().length) out.write("-")
    out.newLine()
    out.write(" ")
    out.write("${lhv * rhv}")
    out.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val out = File(outputName).bufferedWriter()
    var space = ""
    val res = lhv / rhv
    var k = 0
    var def = ""
    var current = lhv.toString()[k].toString()
    while (current.toInt() < rhv && k != lhv.toString().length - 1) {
        k++
        current += lhv.toString()[k]
    }
    if (current.length == (current.toInt() / rhv * rhv).toString().length) out.write(" ")
    out.write("$lhv | $rhv")
    out.newLine()
    if (current.length != (current.toInt() / rhv * rhv).toString().length)
        space += " ".repeat(current.length - (current.toInt() / rhv * rhv).toString().length - 1)
    var spaceres = ""
    spaceres += " ".repeat(lhv.toString().length - current.toInt().toString().length + 3)
    out.write("$space-${current.toInt() / rhv * rhv}$spaceres$res")
    def += if (current.length == (current.toInt() / rhv * rhv).toString().length)
        "-".repeat(current.length + 1)
    else
        "-".repeat(current.length)
    out.newLine()
    out.write(def)
    out.newLine()
    space = if (current.length == (current.toInt() / rhv * rhv).toString().length) " " else ""
    while (k != lhv.toString().length - 1) {
        def = ""
        space += " ".repeat(current.length - (current.toInt() % rhv).toString().length)
        current = (current.toInt() % rhv).toString()
        k++
        current += lhv.toString()[k]
        out.write("$space$current")
        out.newLine()
        var minus = ""
        var space2 = space
        if (current.length != (current.toInt() / rhv * rhv).toString().length)
            minus += " ".repeat(current.length - (current.toInt() / rhv * rhv).toString().length - 1)
        if (current.length == (current.toInt() / rhv * rhv).toString().length)
            space2 = space.substring(0..space.length - 2)
        def += if (current.length == (current.toInt() / rhv * rhv).toString().length)
            "-".repeat(current.length + 1)
        else
            "-".repeat(current.length)
        out.write("$space2$minus-${current.toInt() / rhv * rhv}")
        out.newLine()
        out.write("$space2$def")
        out.newLine()
    }
    space += " ".repeat(current.length - (current.toInt() % rhv).toString().length)
    current = (current.toInt() % rhv).toString()
    out.write("$space$current")
    out.close()
}

