enum class CardType {
    VKPay, VISA, MasterCard
}

fun main() {
    println("Введи сумму перевода: ")
    val cash = readLine()?.toDouble() ?: 0.00
    println("Выберите какой у вас счёт (1 - VISA, 2 - MasterCard или 3 - VKPay. Стандарт VKPay")
    val type = readLine()?.toInt() ?: 3
    println("Сумма перевода за месяц. Дефолт - 0")
    val summTrans = readLine()?.toDouble() ?: 0.00
    println(taxCalc(cash, choiseType(type), summTrans))
}

fun choiseType(number: Int): CardType {
    return when (number) {
        1 -> return CardType.VISA
        2 -> return CardType.MasterCard
        3 -> return CardType.VKPay
        else -> {return CardType.VKPay}
    }
}

fun taxCalc(cash: Double, cardType: CardType = CardType.VKPay, summTrans: Double = 0.00): String {
    if (cardType == CardType.VKPay) {
        if (cash >= 40000.00 || cash + summTrans >= 40000.00) {
            val over = 40000.00 - summTrans
            return "Со счёта VK Pay за месяц можно перевести не более 40.000р 00 коп." + System.lineSeparator() +
                        "В этом месяце вы перевели: $summTrans" + System.lineSeparator() +
                        "Максимум в этом месяце вы можете перевести: $over"
        } else if (cash > 0) {
            return "Комиссия не взимается"
        }
    } else if (cardType == CardType.VISA || cardType == CardType.MasterCard) {
        if (cash >= 600000.00 || cash + summTrans >= 600000.00) {
            val over = 600000.00 - summTrans
            return "С карт VISA, МИР и MasterCard за месяц можно перевести не более 600.000 р 00 коп." + System.lineSeparator() +
                        "В этом месяце вы перевели: $summTrans" + System.lineSeparator() +
                        "Максимум в этом месяце вы можете перевести: $over"
        } else if (cash > 0 && cardType == CardType.VISA) {
            val taxVisa = cash * 0.0075
            val totalTransfer = cash - taxVisa
            if (totalTransfer < 35) {
                val taxFix = 35;
                val totalCash = cash - taxFix;
                return "Комиссия при переводе с карты VISA и МИР: 0,75% , минимум 35р" + System.lineSeparator() +
                            "Комиссия за перевод $cash будет составлять $taxFix" + System.lineSeparator() +
                            "С учётом комиссии на ваш счёт поступит: $totalCash"
            } else if (totalTransfer > 35) {
                val totalCash = cash - totalTransfer;
                return "Комиссия при переводе с карты VISA и МИР: 0,75% , минимум 35р" + System.lineSeparator() +
                            "Комиссия за перевод $cash будет составлять $taxVisa" + System.lineSeparator() +
                            "С учётом комиссии на ваш счёт поступит: $totalCash"
            }

        } else if (cash > 0 && cardType == CardType.MasterCard) {
            val taxMaster = cash * 0.006 + 20
            val totalTransfer = cash - taxMaster
            if (summTrans > 75000) {
                return "Комиссия при переводе с карты MasterCard и Maestro не взимается при сумме перевода до 75.000 в месяц." + System.lineSeparator() +
                            "В иных случаях: 0,6% + 20 рублей." + System.lineSeparator() +
                            "Комиссия за перевод $cash будет составлять $taxMaster" + System.lineSeparator() +
                            "С учётом комиссии на ваш счёт поступит: $totalTransfer"

            } else if (summTrans < 75000 && (cash - summTrans) < 75000) {
                return "Комиссия при переводе с карты MasterCard и Maestro не взимается при сумме перевода до 75.000 в месяц." + System.lineSeparator() +
                            "В иных случаях: 0,6% + 20 рублей." + System.lineSeparator() +
                            "Комиссия за перевод $cash взиматься не будет"
            } else if (summTrans < 75000 && (cash - summTrans) > 75000){
                val difference = cash - (75000 - summTrans)
                val newTax = difference * 0.006 + 20
                val totalTransfer = cash - newTax
                return "Комиссия при переводе с карты MasterCard и Maestro не взимается при сумме перевода до 75.000 в месяц." + System.lineSeparator() +
                            "В иных случаях: 0,6% + 20 рублей." + System.lineSeparator() +
                            "По причине привышения лимита в 75.000 руб. комиссия за перевод $cash будет наложена на сумму $difference руб. и составляет $newTax" + System.lineSeparator() +
                            "С учётом комиссии на ваш счёт поступит: $totalTransfer"
            }
        }
    }
    return "Fail"
}