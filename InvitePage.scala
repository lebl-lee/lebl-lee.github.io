//> using scala "3.6.1"
//> using platform scala-js
//> using dep "org.scala-js::scalajs-dom::2.8.0"
//> using dep "com.lihaoyi::scalatags::0.13.1"
//> using jsModuleKind "es"

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.*

import scala.scalajs.js.annotation.{JSExportTopLevel, JSGlobal, JSName}
import scalatags.JsDom.all.*
import scalatags.JsDom.svgAttrs.viewBox
import scalatags.JsDom.{svgAttrs, svgTags}
import scalatags.JsDom.tags2.{nav, section}

import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.URIUtils

val i18n = attr("data-i18n")

// Add this near the top of the file with other val definitions
val weddingDate = new Date(2025, 7, 29, 15) // Month is 0-based, so 8 is August
val weddingDateFormatted = s"August 29, 2025"

case class InviteCalendar(
    begin: Date,
    end: Date,
    summary: String,
    description: String,
    location: String) {
  def data =
    s"""BEGIN:VCALENDAR
       |VERSION:2.0
       |BEGIN:VEVENT
       |DTSTART:20250929T150000
       |DTEND:20250929T230000
       |SUMMARY:$summary
       |DESCRIPTION:$description
       |LOCATION:$location
       |END:VEVENT
       |END:VCALENDAR""".stripMargin

  def link = s"data:text/calendar;charset=utf-8,${URIUtils.encodeURIComponent(data)}"
}

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

case class I18n(en: String, cs: String, kr: String) {
  def c: String = this.hashCode().toString
  def i18nAttr = (i18n := c)
}
object I18n {
  var dynamicItems = Map[String, I18n]()
  // never in production
  def apply(en: String, cs: String, kr: String) =
    val res = new I18n(en,cs,kr)
    dynamicItems = dynamicItems + (res.c -> res)
    res

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

// Language manager
@JSExportTopLevel("LanguageManager")
object LanguageManager {
  var currentLang = "en"
  @JSExportTopLevel("setLanguage")
  def setLanguage(lang: String): Unit = {
    if (currentLang != lang && Seq("en", "cs", "kr").contains(lang)) {
      currentLang = lang
      updatePageLanguage()
    }
  }
  @JSExportTopLevel("getText")
  def getText(key: String): String = {
    I18n.dynamicItems
      .get(key)
      .map { translation =>
        currentLang match {
          case "en" => translation.en
          case "cs" => translation.cs
          case "kr" => translation.kr
          case _    => translation.en // fallback to English
        }
      }
      .getOrElse(key)
  }
  private def updatePageLanguage(): Unit = {
    import org.scalajs.dom.document
    document.querySelectorAll(selectors = "[data-i18n]").foreach { node =>
      val key = node.getAttribute("data-i18n")
      node.textContent = getText(key)
    }
  }
}

object Elements {
  def downloadCalendar(calendar: InviteCalendar) =
    a(
      href := Wedding.inviteCalendar.link,
      download := "wedding-invitation.ics",
      cls := "inline-flex items-center px-4 py-2 bg-pink-600 text-white rounded-md hover:bg-pink-700 transition-colors"
    )(
      svgTags.svg(
        xmlns := "http://www.w3.org/2000/svg",
        cls := "h-5 w-5 mr-2",
        viewBox := "0 0 20 20",
        svgAttrs.fill := "currentColor"
      )(
        svgTags.path(
          svgAttrs.fillRule := "evenodd",
          svgAttrs.d := "M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z",
          svgAttrs.clipRule := "evenodd"
        )
      ),
      "Add to Calendar"
    )
}


// Scala.js
@main def WeddingInvite(): Unit = {
  val lang = LanguageManager.currentLang

  // script https://cdn.tailwindcss.com
  val navBar = nav(cls := "bg-white shadow-md sticky top-0 z-50")(
    div(cls := "max-w-6xl mx-auto px-4 py-3 flex justify-between items-center")(
      a(href := "#", cls := "text-xl font-semibold text-pink-600 script-font")(
        "David & Suhyoon"
      ),
      ul(cls := "flex space-x-4 text-gray-700")(
//        li(a(href := "#invite", cls := "hover:text-pink-600")("Invite")),
//        li(a(href := "#our-story", cls := "hover:text-pink-600")("Our Story")),
//        li(a(href := "#phonebook", cls := "hover:text-pink-600")("Photobook")),
//        li(a(href := "#schedule", cls := "hover:text-pink-600")("Calendar")),

        li(
          a(href := "#rsvp", cls := "hover:text-pink-600 font-semibold")("RSVP")
        ),
        li(cls := "relative flex items-center group")(
          button(
            id := "langSelect",
            cls := "bg-transparent border-none cursor-pointer hover:text-pink-600 flex items-center space-x-1 px-2 py-1",
            onclick := "this.nextElementSibling.classList.toggle('hidden')"
          )(
            span(id := "selectedLang")("EN"),
            svgTags.svg(
              xmlns := "http://www.w3.org/2000/svg",
              cls := "h-4 w-4",
              viewBox := "0 0 20 20",
              svgAttrs.fill := "currentColor"
            )(
              svgTags.path(
                svgAttrs.fillRule := "evenodd",
                svgAttrs.d := "M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z",
                svgAttrs.clipRule := "evenodd"
              )
            )
          ),
          div(
            id := "langSelectDropdown",
            cls := "absolute top-full right-0 mt-1 bg-white shadow-lg rounded-md hidden py-1 min-w-[80px]",
            onclick := "event.stopPropagation()"
          )(
            div(
              cls := "px-4 py-2 hover:bg-gray-100 cursor-pointer",
              onclick := { () =>
                document.getElementById("selectedLang").textContent = "EN"
                document.getElementById("langSelectDropdown").classList.add("hidden")
                LanguageManager.setLanguage("en")
              }
            )("EN"),
            div(
              cls := "px-4 py-2 hover:bg-gray-100 cursor-pointer",
              onclick := { () =>
                document.getElementById("selectedLang").textContent = "CS"
                document.getElementById("langSelectDropdown").classList.add("hidden")
                LanguageManager.setLanguage("cs")
              }
            )("CS"),
            div(
              cls := "px-4 py-2 hover:bg-gray-100 cursor-pointer",
              onclick := { () =>
                document.getElementById("selectedLang").textContent = "KR"
                document.getElementById("langSelectDropdown").classList.add("hidden")
                LanguageManager.setLanguage("kr")
              }
            )("KR")
          )
        )
      )
    )
  )

  val headerSection = header(
    id := "invite",
    cls := "bg-white text-center py-10 flex justify-center px-4"
  )(
    // Card container
    div(
      cls := "max-w-[1200px] w-full bg-white rounded-2xl shadow-lg overflow-hidden"
    )(
      // Image and content container
      div(
        cls := "relative min-h-[50vh] flex items-end justify-center bg-cover bg-top",
        style := "background-image: url('./images/header-bg.jpg')"
      )(
        // Navigation buttons container - absolute positioned at top
        div(
          cls := "absolute top-8 inset-x-8 flex justify-between z-20"
        )(
          // Left button - Our Story
          a(
            href := "#our-story",
            cls := "px-6 py-2 border-2 border-white text-white rounded-full " +
              "backdrop-blur-sm bg-white/10 hover:bg-white/30 transition-all " +
              "font-medium text-sm uppercase tracking-wider",
            i18n := I18n.ourStory.c
          )(I18n.ourStory.en),
          // Right button - Photobook
          a(
            href := "./photobook.pdf",
            cls := "px-6 py-2 border-2 border-white text-white rounded-full " +
              "backdrop-blur-sm bg-white/10 hover:bg-white/30 transition-all " +
              "font-medium text-sm uppercase tracking-wider",
            i18n := I18n.photobook.c
          )((I18n.photobook.en))
        ),
        // Gradient overlay for bottom text
        div(cls := "absolute inset-x-0 bottom-0 h-48 bg-gradient-to-t from-black/40 to-transparent"),
        // Content container
        div(cls := "relative z-10 px-4 pb-16")(
          h1(cls := "text-6xl script-font text-white")(Wedding.brideAndGroomName),
          p(cls := "text-2xl mt-4 text-white", i18n := I18n.gettingMaried.c)(I18n.gettingMaried.en)
        )
      )
    )
  )

  // Add this countdown section after the headerSection:
  val countdownSection = section(cls := "py-6 text-center bg-pink-50")(
    h3(cls := "text-2xl mb-4 text-gray-800", i18n := I18n.countdownTitle.c)(I18n.countdownTitle.en),
    div(cls := "grid grid-cols-4 max-w-2xl mx-auto gap-4")(
      div(cls := "bg-white p-4 rounded-lg shadow-sm")(
        div(id := "days", cls := "text-3xl font-bold text-pink-600")("0"),
        div(cls := "text-gray-600", i18n := I18n.days.c)("Days")
      ),
      div(cls := "bg-white p-4 rounded-lg shadow-sm")(
        div(id := "hours", cls := "text-3xl font-bold text-pink-600")("0"),
        div(cls := "text-gray-600", i18n := I18n.hours.c)("Hours")
      ),
      div(cls := "bg-white p-4 rounded-lg shadow-sm")(
        div(id := "minutes", cls := "text-3xl font-bold text-pink-600")("0"),
        div(cls := "text-gray-600", i18n := I18n.minutes.c)("Minutes")
      ),
      div(cls := "bg-white p-4 rounded-lg shadow-sm")(
        div(id := "seconds", cls := "text-3xl font-bold text-pink-600")("0"),
        div(cls := "text-gray-600", i18n := I18n.seconds.c)("Seconds")
      )
    )
  )

  val inviteText = section(cls := "bg-white text-center py-8 px-4")(
    p(cls := "text-lg max-w-2xl mx-auto mb-8", Wedding.inviteText.i18nAttr)(
      "We invite you to celebrate our wedding day with us on ",
      strong(weddingDateFormatted),
      " at the beautiful ",
      strong(Wedding.address),
      "."
    ),
    // Action buttons container
    div(cls := "max-w-2xl mx-auto mb-8")(Elements.downloadCalendar(Wedding.inviteCalendar)),
    // Map and RSVP container with grid
    div(cls := "max-w-5xl mx-auto grid md:grid-cols-2 gap-8")(
      // Left column with Venue Image and Map as separate cards
      div(cls := "space-y-8")(
        // Venue Image Card
        div(cls := "rounded-lg overflow-hidden shadow-lg bg-white")(
          div(cls := "relative h-[300px]")(
            img(
              src := "./images/venue.jpg",
              alt := "Wedding Venue",
              cls := "w-full h-full object-cover"
            )
          ),
          div(cls := "p-4 border-t")(
            h3(cls := "text-lg font-semibold text-gray-800 mb-2")(Wedding.addressBuilding),
            p(cls := "text-gray-600 text-sm")(Wedding.addressLocation)
          )
        ),
        // Map Card
        div(cls := "rounded-lg overflow-hidden shadow-lg bg-white")(
          div(cls := "h-[300px]")(
            iframe(
              cls := "w-full h-full border-0",
              src := "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2560.8876148467394!2d14.445713776892892!3d50.07151717132046!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x470b948c0f39be23%3A0xb9b2316bc33db613!2zQWx0w6FuIEdyw6lib3ZrYQ!5e0!3m2!1sen!2sus!4v1710394895015!5m2!1sen!2sus",
              attr("allowfullscreen") := "",
              attr("loading") := "lazy",
              attr("referrerpolicy") := "no-referrer-when-downgrade"
            )
          ),
          div(cls := "p-4 border-t")(
            div(
              a(
                href := "https://goo.gl/maps/1HrQUoYDY9Q2diyr9",
                target := "_blank",
                cls := "inline-flex items-center text-pink-600 hover:text-pink-700 transition-colors"
              )(
                svgTags.svg(
                  xmlns := "http://www.w3.org/2000/svg",
                  cls := "h-5 w-5 mr-2",
                  viewBox := "0 0 20 20",
                  svgAttrs.fill := "currentColor"
                )(
                  svgTags.path(
                    svgAttrs.fillRule := "evenodd",
                    svgAttrs.d := "M5.05 4.05a7 7 0 119.9 9.9L10 18.9l-4.95-4.95a7 7 0 010-9.9zM10 11a2 2 0 100-4 2 2 0 000 4z",
                    svgAttrs.clipRule := "evenodd"
                  )
                ),
                "Open in Google Maps"
              )
            )
          )
        )
      ),
      // Right side: RSVP Form - matched height with map container
      div(id := "rsvp", cls := "bg-white p-6 overflow-y-auto")(
        h2(cls := "text-3xl font-semibold text-center mb-6")("Kindly RSVP"),
        div(cls := "flex justify-center")(
          button(
            onclick := "window.open('https://docs.google.com/forms/d/e/1FAIpQLSewvKPUdqtivGGJftq1vbMjmIOvHZ6yhmoiC2xURIkT45jFLQ/viewform', '_blank')",
            cls := "inline-flex items-center font-semibold text-pink-600 hover:text-pink-700 transition-colors gap-2 mb-4 bg-white border-2 border-pink-600 px-4 py-2 rounded hover:bg-pink-50"
          )(
            svgTags.svg(
              xmlns := "http://www.w3.org/2000/svg",
              cls := "h-5 w-5",
              viewBox := "0 0 20 20",
              svgAttrs.fill := "currentColor"
            )(
              svgTags.path(
                svgAttrs.d := "M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zm-2.207 2.207L3 14.172V17h2.828l8.38-8.379-2.83-2.828z"
              )
            ),
            "Fill out Google RSVP Form (en)"
          )
        ),
        div(cls := "my-4 flex justify-center items-center gap-4")(
          div(cls := "flex-grow border-t border-gray-300"),
          p(cls := "text-gray-500 px-4")("or"),
          div(cls := "flex-grow border-t border-gray-300")
        ),
        p(cls := "text-red-600 mb-4 text-center")("RSVP form not working yet, please use the Google Form link above"),
        form(id := "rsvpForm", cls := "space-y-4")(
          input(
            `type` := "text",
            placeholder := "Your Full Name",
            required := true,
            cls := "w-full border border-gray-300 p-3 rounded",
            disabled := true
          ),
          input(
            `type` := "email",
            placeholder := "Email Address",
            required := true,
            cls := "w-full border border-gray-300 p-3 rounded"
          ),
          select(
            required := true,
            cls := "w-full border border-gray-300 p-3 rounded"
          )(
            option(value := "")("Will you attend?"),
            option(value := "yes")("Yes, can't wait!"),
            option(value := "no")("Sorry, can't make it")
          ),
          button(
            `type` := "submit",
            disabled := true,
            cls := "bg-gray-400 cursor-not-allowed text-white px-6 py-2 rounded w-full"
          )("Submit RSVP (Not Working)"),
          p(
            id := "rsvpMessage",
            cls := "text-green-600 text-center mt-4 hidden"
          )("Thank you for your RSVP!")
        ),
        // Guest List Stats
        div(cls := "mt-6 p-4 bg-gray-50 rounded-lg")(
          div(cls := "text-center space-y-4")(
            div(
              span(
                id := "confirmedCount",
                cls := "text-3xl font-bold text-pink-600"
              )("20"),
              p(cls := "text-gray-600 text-sm")("Confirmed Guests")
            ),
            div(cls := "mt-4")(
              div(cls := "text-sm text-gray-600 mb-2 flex justify-between")(
                span("Response Rate"),
                span(id := "responseRate")("50%")
              ),
              div(cls := "w-full bg-gray-200 rounded-full h-2")(
                div(
                  id := "responseRateBar",
                  cls := "bg-pink-600 rounded-full h-2 transition-all duration-500",
                  style := "width: 50%"
                )
              )
            ),
            p(cls := "text-sm text-gray-600")(
              "Venue Capacity: ",
              span(cls := "font-semibold")("40 guests")
            )
          )
        )
      )
    )
  )

  // Add this section after the inviteText and before the rsvpSection:
  val scheduleSection =
    section(id := "schedule", cls := "py-12 px-6 bg-gray-50")(
      h2(
        cls := "text-3xl font-semibold text-center mb-8 script-font text-pink-600",
        I18n.scheduleTitle.i18nAttr
      ),
      div(cls := "max-w-5xl mx-auto grid md:grid-cols-2 gap-8 items-start")(
        // Left side: Schedule
        div(cls := "space-y-8")(
          // Pre-wedding day schedule
          WeddingTimeline.day0.render,
          // Wedding day schedule
          WeddingTimeline.day1.render,
        ),
        // Right side: Image placeholder
        div(
          cls := "relative aspect-[3/4] bg-gray-100 rounded-lg overflow-hidden shadow-lg"
        )(
          img(
            src := "./images/wp-1-dress.jpg",
            alt := "Wedding Schedule Decoration",
            cls := "w-full h-full object-cover",
            onerror := "this.src='data:image/svg+xml,%3Csvg xmlns=\"http://www.w3.org/2000/svg\" width=\"100%\" height=\"100%\" viewBox=\"0 0 100 100\"%3E%3Crect width=\"100%\" height=\"100%\" fill=\"%23f3f4f6\"%3E%3C/rect%3E%3Ctext x=\"50%\" y=\"50%\" dominant-baseline=\"middle\" text-anchor=\"middle\" font-family=\"sans-serif\" font-size=\"14\" fill=\"%239ca3af\"%3EImage Coming Soon%3C/text%3E%3C/svg%3E'"
          ),
          div(
            cls := "absolute inset-0 bg-gradient-to-t from-black/10 to-transparent"
          )
        )
      )
    )

  val previewLinksSection = section(cls := "py-12 px-6")(
    div(cls := "max-w-6xl mx-auto")(
      h2(
        cls := "text-3xl font-semibold text-center mb-8 script-font text-pink-600"
      )(
        "Explore More"
      ),
      div(cls := "grid md:grid-cols-3 gap-8")(
        // Photo Book Preview Card
        a(href := "#photo-book", cls := "group")(
          div(
            cls := "bg-white rounded-lg shadow-md overflow-hidden transition transform hover:-translate-y-1 hover:shadow-xl"
          )(
            div(cls := "relative aspect-[4/3]")(
              img(
                src := "./images/photo-book-preview.jpg",
                alt := "Photo Book Preview",
                cls := "w-full h-full object-cover",
                onerror := "this.src='data:image/svg+xml,%3Csvg xmlns=\"http://www.w3.org/2000/svg\" width=\"100%\" height=\"100%\" viewBox=\"0 0 100 100\"%3E%3Crect width=\"100%\" height=\"100%\" fill=\"%23f3f4f6\"%3E%3C/rect%3E%3Ctext x=\"50%\" y=\"50%\" dominant-baseline=\"middle\" text-anchor=\"middle\" font-family=\"sans-serif\" font-size=\"14\" fill=\"%239ca3af\"%3EImage Coming Soon%3C/text%3E%3C/svg%3E'"
              )
            ),
            div(cls := "p-4")(
              h3(cls := "text-xl font-semibold mb-2 text-pink-600")(
                "Photo Book"
              ),
              p(cls := "text-gray-600")("Collection of our precious moments")
            )
          )
        ),
        // Recommendations Preview Card
        a(href := "#recommendation", cls := "group")(
          div(
            cls := "bg-white rounded-lg shadow-md overflow-hidden transition transform hover:-translate-y-1 hover:shadow-xl"
          )(
            div(cls := "relative aspect-[4/3]")(
              img(
                src := "./images/recommendations-preview.jpg",
                alt := "Recommendations Preview",
                cls := "w-full h-full object-cover",
                onerror := "this.src='data:image/svg+xml,%3Csvg xmlns=\"http://www.w3.org/2000/svg\" width=\"100%\" height=\"100%\" viewBox=\"0 0 100 100\"%3E%3Crect width=\"100%\" height=\"100%\" fill=\"%23f3f4f6\"%3E%3C/rect%3E%3Ctext x=\"50%\" y=\"50%\" dominant-baseline=\"middle\" text-anchor=\"middle\" font-family=\"sans-serif\" font-size=\"14\" fill=\"%239ca3af\"%3EImage Coming Soon%3C/text%3E%3C/svg%3E'"
              )
            ),
            div(cls := "p-4")(
              h3(cls := "text-xl font-semibold mb-2 text-pink-600")(
                "Prague Recommendations"
              ),
              p(cls := "text-gray-600")("Places to visit and things to do")
            )
          )
        )
      )
    )
  )

  // Add these style definitions to create a pulse animation when numbers change
  val styles = scalatags.JsDom.tags2.style(
    """
    @keyframes pulse {
      0% { transform: scale(1); }
      50% { transform: scale(1.05); }
      100% { transform: scale(1); }
    }
    .number-update {
      animation: pulse 0.5s ease-in-out;
    }
    """
  )

  val tailwindCss = script(src := "https://cdn.tailwindcss.com")
  document.head.appendChild(tailwindCss.render)

  document.head.appendChild(styles.render)
  // Add the styles to the content:
  val content = div(
    navBar,
    countdownSection,
    headerSection,
    inviteText,
    scheduleSection,
    ourStorySection,
    previewLinksSection
  )
  document.body.appendChild(content.render)

  val formElem = document.getElementById("rsvpForm")
  val messageElem = document.getElementById("rsvpMessage")

  formElem.addEventListener(
    "submit",
    (e: dom.Event) => {
      e.preventDefault()
      messageElem.classList.remove("hidden")
    }
  )

  // The countdown code now uses the same weddingDate val
  def updateCountdown(): Unit = {
    val now = new Date()
    val distance = weddingDate.getTime() - now.getTime()

    val days = Math.floor(distance / (1000 * 60 * 60 * 24)).toInt
    val hours =
      Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)).toInt
    val minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60)).toInt
    val seconds = Math.floor((distance % (1000 * 60)) / 1000).toInt

    document.getElementById("days").textContent = days.toString
    document.getElementById("hours").textContent = hours.toString
    document.getElementById("minutes").textContent = minutes.toString
    document.getElementById("seconds").textContent = seconds.toString
  }

  // Initial call to display countdown immediately
  updateCountdown()
  LanguageManager.setLanguage(lang)

  // Update the countdown every second
  dom.window.setInterval(() => updateCountdown(), 1000)

}

val ourStorySection = section(id := "our-story", cls := "py-16 bg-white")(
  // Hero/Intro part
  div(cls := "relative h-[60vh] mb-16")(
    // Background image with overlay
    div(
      cls := "absolute inset-0 bg-cover bg-center",
      style := "background-image: url('./images/our-story.jpg')"
    ),
    div(
      cls := "absolute inset-0 bg-black/40"
    ), // Overlay for better text visibility
    // Content
    div(cls := "relative h-full flex items-center justify-center text-center")(
      div(cls := "max-w-3xl px-4")(
        h2(cls := "text-5xl text-white mb-4 script-font")("Our Love Story"),
        p(cls := "text-xl text-white")("The journey that brought us together"),
        p(cls := "text-xl text-white")("10.5.2021 - 29.8.2025")
      )
    )
  ),

  // Timeline section
  div(cls := "max-w-6xl mx-auto px-4")(
    // Timeline container
    div(cls := "space-y-16")(
      // Timeline items
      timelineItem(
        "First Meet",
        "May 2021",
        "We met in Korea during David’s masters exchange program. Despite arriving just three months before, David knew the best cafes in Seoul for dates.",
        "./images/first-data-cafe.jpg"
      ),
      timelineItem(
        "Long Distance Relationship",
        "January 2022",
        "David returned to Czech to finish his masters studies at his home university, starting our long distance relationship.",
        "./images/long-distance-rs.jpg",
        true // Reverse layout
      ),
      timelineItem(
        "Easter",
        "April 2022",
        "We stuck to a meet-every-three-months rule for our long distance relationship. Suhyoon visited David for Easter, working remotely Com Prague for a month.",
        "./images/eastern-2022.jpg"
      ),
      timelineItem(
        "Moving In Together",
        "December 2022 - May 2023",
        "Suhyoon visited Prague again, staying six months while preparing her architecture school portfolio. David graduated Com his computer science masters program in Spring.",
        "./images/david-graduation.jpg",
        true
      ),
      timelineItem(
        "Remote",
        "June to October 2023",
        "David took a remote job and joins Suhyoon in Cheongdo, Korea, for her traditional building course.Afterwards, Suhyoon starts her three-year architecture studies in London. David works from both Prague and London.",
        "./images/cheongdo-cafe.jpg"
      ),
      timelineItem(
        "The Proposal",
        "1st January 2024",
        "David proposes during a morning walk on the beach while vacationing in Egypt. Suhyoon said yes.",
        "./images/proposal.jpg",
        true
      ),
      timelineItem(
        "Wedding",
        "29th August 2025",
        "After 1.5 years of juggling work, studies, moving, wedding planning... WE’RE GETTING MARRIED!! Come celebrate with us!!",
        "./images/dress-running.jpg"
      )
    )
  )
)

def timelineItem(
    title: String,
    date: String,
    description: String,
    imageSrc: String,
    reverse: Boolean = false
) = {
  div(cls := s"flex flex-col ${
      if (reverse) "md:flex-row-reverse" else "md:flex-row"
    } items-center gap-8")(
    // Image container
    div(cls := "w-full md:w-1/2")(
      div(cls := "relative aspect-[4/3] rounded-lg overflow-hidden shadow-lg")(
        img(
          src := imageSrc,
          alt := title,
          cls := "w-full h-full object-cover",
          onerror := "this.src='data:image/svg+xml,%3Csvg xmlns=\"http://www.w3.org/2000/svg\" width=\"100%\" height=\"100%\" viewBox=\"0 0 100 100\"%3E%3Crect width=\"100%\" height=\"100%\" fill=\"%23f3f4f6\"%3E%3C/rect%3E%3Ctext x=\"50%\" y=\"50%\" dominant-baseline=\"middle\" text-anchor=\"middle\" font-family=\"sans-serif\" font-size=\"14\" fill=\"%239ca3af\"%3EImage Coming Soon%3C/text%3E%3C/svg%3E'"
        )
      )
    ),
    // Content container
    div(cls := "w-full md:w-1/2 text-center md:text-left")(
      div(cls := s"${if (reverse) "md:pr-8" else "md:pl-8"}")(
        span(cls := "text-sm text-pink-600 font-semibold")(date),
        h3(cls := "text-2xl font-semibold mt-2 mb-4")(title),
        p(cls := "text-gray-600")(description)
      )
    )
  )
}
