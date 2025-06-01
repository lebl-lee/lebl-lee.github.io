
import scala.scalajs.js.annotation.{JSExportTopLevel, JSGlobal, JSName}
import scalatags.JsDom.all.*
import scalatags.JsDom.svgAttrs.viewBox
import scalatags.JsDom.{svgAttrs, svgTags}
import scalatags.JsDom.tags2.{nav, section}

import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.URIUtils

// Add this near the top of the file with other val definitions
val weddingDate = new Date(2025, 7, 29, 15) // Month is 0-based, so 8 is August
val weddingDateFormatted = s"August 29, 2025"

object Wedding {
  val weddingDateStart = new Date(2025, 7, 29, 15)
  val weddingDateFormatted = s"${weddingDateStart.getDate()}.${weddingDateStart.getMonth() + 1}.${weddingDateStart.getFullYear()}"

  val brideName = "Suhyoon"
  val groomName = "David"
  val brideAndGroomName = s"$brideName & $groomName"
  val addressBuilding = "Altán Grebovka"
  val addressLocation = "Havlíckovy sady 1369, 120 00 Praha 2-Vinohrady, Czechia"
  val address = s"$addressBuilding, $addressLocation"
  val inviteText = I18n(
    s"We invite you to celebrate our wedding day with us on $weddingDateFormatted at the beautiful ${Wedding.address}",
    s"Srdečně Vás zveme na oslavu našeho svatebního dne, který se koná $weddingDateFormatted v ${Wedding.address}",
    s"${weddingDateFormatted}에 아름다운 ${Wedding.address}에서 열리는 저희의 결혼식에 여러분을 초대합니다"
  )
  val inviteCalendar = InviteCalendar(
    begin = weddingDateStart,
    end = new Date(2025, 7, 29, 23),
    summary = "David & Suhyoon's Wedding",
    description = "We're getting married! Join us for our special day.",
    location = "Altán Grebovka, Havlíckovy sady 1369, 120 00 Praha 2-Vinohrady, Czechia"
  )
}

object WeddingTimeline {
  case class Day(name: I18n, date: Date, events: List[Event]) {
    def render = div(
      h3(cls := "text-2xl font-semibold mb-6 text-gray-800")(
        s"${date.toLocaleDateString()} - ${name.en}"
      ),
      div(cls := "space-y-6")(
        events.map(event =>
          div(cls := "flex items-center space-x-4")(
            div(cls := "text-right w-24 font-semibold text-pink-600")(
              new js.Date(event.time.getTime()).toLocaleTimeString()
            ),
            div(cls := "w-px h-12 bg-pink-300"),
            div(cls := "flex-1")(
              p(cls := "font-medium", event.title.i18nAttr)(event.title.en),
              event.description.map(desc =>
                p(cls := "text-gray-600 text-sm")(desc)
              )
            )
          )
        )
      )
    )
  }
  case class Event(time: Date, title: I18n, description: List[String])

  val day0 = Day(
    I18n("Pre-Wedding", "Den před svatbou", "웨딩 전날"),
    new Date(2025, 7, 28),
    List(
      Event(new Date(2025, 7, 28, 11),
        I18n("Welcome WORKOUT", "Uvítací cvičení", "환영 운동"),
        List("@F45 Prague")),
      Event(new Date(2025, 7, 28, 13),
        I18n("Welcome DRINKS", "Uvítací nápoje", "환영 음료"),
        List(
          "@Cerveny Jelen",
          "Include RED in your outfit for free pilsners & bar treats from us :)"
        ))
    ))

  val day1 = Day(
    I18n("Wedding Day", "Svatební den", "결혼식 당일"),
    Wedding.weddingDateStart,
    List(
      Event(new Date(2025, 7, 29, 14, 15),
        I18n("Reception", "Příjem", "리셉션"),
        List("Welcome drinks in cafe area")),
      Event(new Date(2025, 7, 29, 15),
        I18n("Ceremony", "Obřad", "결혼식"),
        List(
          "Guest seated by 14:45 in upstairs pavillion",
          "15:30 Bestie BLESSINGs",
          "15:45 Round of Ribbons",
          "16:00 Group PHOTOSHOOT"
        )),
      Event(new Date(2025, 7, 29, 17),
        I18n("Dinner", "Večeře", "저녁 식사"),
        List("opened with toast by parents")),
      Event(new Date(2025, 7, 29, 18, 30),
        I18n("Cake CUTTING", "Krájení dortu", "케이크 커팅"),
        List()),
      Event(new Date(2025, 7, 29, 19),
        I18n("Dessert & DJ Set", "Dezert & DJ Set", "디저트 & DJ 세트"),
        List()),
      Event(new Date(2025, 7, 29, 20),
        I18n("After Party", "After Party", "애프터 파티"),
        List(
          "Dancing and celebrations",
          "20:00 Sister Act",
          "20:30 Speed Luiz",
          "21:00 Surprise Performance by Bride & Groom"
        )),
      Event(new Date(2025, 7, 29, 22),
        I18n("Farewell", "Rozloučení", "작별 인사"),
        List("Music close at 10 PM, venue open until 11:30 PM for last goodbyes"))
    ))
}


object WeddingText {
  val ourStory = I18n("Our Story", "Náš Příběh", "우리의 이야기")
  val photobook = I18n("Photobook", "Fotokniha", "포토북")
  val gettingMaried = I18n(
    "Are Getting Married!",
    "Se Berou!",
    "결혼합니다!"
  )
  val countdownTitle = I18n(
    "Counting down to our special day",
    "Odpočítávání do našeho velkého dne",
    "우리의 특별한 날까지 남은 시간"
  )
  val rsvp = I18n(
    "Kindly RSVP",
    "Prosím potvrďte účast",
    "참석 여부를 알려주세요"
  )
  val rsvpName = I18n("Your Full Name", "Vaše celé jméno", "성함")
  val rsvpEmail = I18n("Email Address", "Emailová adresa", "이메일 주소")
  val rsvpWillAttend = I18n(
    "Will you attend?",
    "Zúčastníte se?",
    "참석하시겠습니까?"
  )

  val scheduleTitle = I18n(
    "Wedding Schedule",
    "Program svatby",
    "결혼식 일정"
  )
  val scheduleDay0 = I18n(
    "Pre-Wedding",
    "Den před svatbou",
    "웨딩 전날"
  )
  val scheduleDay1 = I18n(
    "Wedding Day",
    "Svatební den",
    "결혼식 당일"
  )

  val days = I18n("Days", "Dny", "일")
  val hours = I18n("Hours", "Hodiny", "시간")
  val minutes = I18n("Minutes", "Minuty", "분")
  val seconds = I18n("Seconds", "Sekundy", "초")

}