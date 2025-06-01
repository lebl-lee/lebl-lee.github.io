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
  val weddingDateFormatted =
    s"${weddingDateStart.getDate()}.${weddingDateStart.getMonth() + 1}.${weddingDateStart.getFullYear()}"

  val brideName = "Suhyoon"
  val groomName = "David"
  val brideAndGroomName = s"$brideName & $groomName"
  val addressBuilding = "Altán Grebovka"
  val addressLocation =
    "Havlíckovy sady 1369, 120 00 Praha 2-Vinohrady, Czechia"
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
    location =
      "Altán Grebovka, Havlíckovy sady 1369, 120 00 Praha 2-Vinohrady, Czechia"
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
      Event(
        new Date(2025, 7, 28, 11),
        I18n("Welcome WORKOUT", "Uvítací cvičení", "환영 운동"),
        List("@F45 Prague")
      ),
      Event(
        new Date(2025, 7, 28, 13),
        I18n("Welcome DRINKS", "Uvítací nápoje", "환영 음료"),
        List(
          "@Cerveny Jelen",
          "Include RED in your outfit for free pilsners & bar treats from us :)"
        )
      )
    )
  )

  val day1 = Day(
    I18n("Wedding Day", "Svatební den", "결혼식 당일"),
    Wedding.weddingDateStart,
    List(
      Event(
        new Date(2025, 7, 29, 14, 15),
        I18n("Reception", "Příjem", "리셉션"),
        List("Welcome drinks in cafe area")
      ),
      Event(
        new Date(2025, 7, 29, 15),
        I18n("Ceremony", "Obřad", "결혼식"),
        List(
          "Guest seated by 14:45 in upstairs pavillion",
          "15:30 Bestie BLESSINGs",
          "15:45 Round of Ribbons",
          "16:00 Group PHOTOSHOOT"
        )
      ),
      Event(
        new Date(2025, 7, 29, 17),
        I18n("Dinner", "Večeře", "저녁 식사"),
        List("opened with toast by parents")
      ),
      Event(
        new Date(2025, 7, 29, 18, 30),
        I18n("Cake CUTTING", "Krájení dortu", "케이크 커팅"),
        List()
      ),
      Event(
        new Date(2025, 7, 29, 19),
        I18n("Dessert & DJ Set", "Dezert & DJ Set", "디저트 & DJ 세트"),
        List()
      ),
      Event(
        new Date(2025, 7, 29, 20),
        I18n("After Party", "After Party", "애프터 파티"),
        List(
          "Dancing and celebrations",
          "20:00 Sister Act",
          "20:30 Speed Luiz",
          "21:00 Surprise Performance by Bride & Groom"
        )
      ),
      Event(
        new Date(2025, 7, 29, 22),
        I18n("Farewell", "Rozloučení", "작별 인사"),
        List(
          "Music close at 10 PM, venue open until 11:30 PM for last goodbyes"
        )
      )
    )
  )
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

case class TimelineItem(
    title: I18n,
    date: Date,
    description: I18n,
    imageSrc: String,
    reverse: Boolean = false
)

object OurStory:
  val title = I18n("Our Love Story", "Náš příběh lásky", "우리의 사랑 이야기")
  val description = I18n("The journey that brought us together", "Cesta, která nás svedla dohromady", "우리를 하나로 만든 여정")
  val storyTimerange = "10.5.2021 - 29.8.2025" // (new Date(2021,4,10), new Date(2025,7,29)).toString()
  val timeline = List(
    TimelineItem(
      I18n("First Meet", "První setkání", "첫 만남"),
      new Date(2021, 4),
      I18n(
        "We met in Korea during David's masters exchange program. Despite arriving just three months before, David knew the best cafes in Seoul for dates.",
        "Potkali jsme se v Koreji během Davidova magistra na výměnném pobytu. I přesto, že přijel jen před třemi měsíci, znal David ty nejlepší kavárny v Soulu na rande.",
        "데이비드의 석사 교환 학생 프로그램 기간 동안 한국에서 만났습니다. 도착한 지 3개월 밖에 되지 않았지만, 데이비드는 데이트하기 좋은 서울의 카페들을 잘 알고 있었죠."
      ),
      "./images/first-data-cafe.jpg"
    ),
    TimelineItem(
      I18n("Long Distance Relationship", "Vztah na dálku", "장거리 연애"),
      new Date(2022, 0),
      I18n(
        "David returned to Czech to finish his masters studies at his home university, starting our long distance relationship.",
        "David se vrátil do Česka dokončit magisterské studium na své domovské univerzitě, čímž začal náš vztah na dálku.",
        "데이비드가 본교에서 석사 과정을 마치기 위해 체코로 돌아가면서 우리의 장거리 연애가 시작되었습니다."
      ),
      "./images/long-distance-rs.jpg"
    ),
    TimelineItem(
      I18n("Easter", "Velikonoce", "부활절"),
      new Date(2022, 3),
      I18n(
        "We stuck to a meet-every-three-months rule for our long distance relationship. Suhyoon visited David for Easter, working remotely Com Prague for a month.",
        "Drželi jsme se pravidla vidět se každé tři měsíce během našeho vztahu na dálku. Suhyoon navštívila Davida na Velikonoce a měsíc pracovala vzdáleně z Prahy.",
        "우리는 3개월에 한 번씩 만나자는 장거리 연애 규칙을 지켰어요. 수현이 부활절에 데이비드를 방문해서 한 달 동안 프라하에서 원격 근무를 했죠."
      ),
      "./images/eastern-2022.jpg"
    ),
    TimelineItem(
      I18n("Moving In Together", "Společné bydlení", "동거 시작"),
      new Date(2022, 11),
      I18n(
        "Suhyoon visited Prague again, staying six months while preparing her architecture school portfolio. David graduated Com his computer science masters program in Spring.",
        "Suhyoon znovu navštívila Prahu a zůstala šest měsíců, během kterých připravovala své portfolio na školu architektury. David na jaře promoval z magisterského programu informatiky.",
        "수현이 다시 프라하를 방문해서 건축학교 포트폴리오를 준비하며 6개월을 머물렀어요. 데이비드는 봄에 컴퓨터 공학 석사 과정을 졸업했습니다."
      ),
      "./images/david-graduation.jpg"
    ),
    TimelineItem(
      I18n("Remote", "Na dálku", "원격 근무"),
      new Date(2023, 5),
      I18n(
        "David took a remote job and joins Suhyoon in Cheongdo, Korea, for her traditional building course.Afterwards, Suhyoon starts her three-year architecture studies in London. David works from both Prague and London.",
        "David si našel práci na dálku a připojil se k Suhyoon v Cheongdo v Koreji během jejího kurzu tradičního stavitelství. Poté Suhyoon začala tříleté studium architektury v Londýně. David pracuje z Prahy i Londýna.",
        "데이비드가 원격 근무를 시작하고 청도에서 수현의 한옥 과정에 합류했어요. 그 후 수현이 런던에서 3년간의 건축 공부를 시작했고, 데이비드는 프라하와 런던을 오가며 일하고 있습니다."
      ),
      "./images/cheongdo-cafe.jpg"
    ),
    TimelineItem(
      I18n("The Proposal", "Zásnuby", "프로포즈"),
      new Date(2024, 0, 1),
      I18n(
        "David proposes during a morning walk on the beach while vacationing in Egypt. Suhyoon said yes.",
        "David požádal Suhyoon o ruku během ranní procházky po pláži na dovolené v Egyptě. Suhyoon řekla ano.",
        "이집트 휴가 중 아침 해변 산책에서 데이비드가 프로포즈를 했고, 수현이 승낙했습니다."
      ),
      "./images/proposal.jpg"
    ),
    TimelineItem(
      I18n("Wedding", "Svatba", "결혼식"),
      new Date(2024, 7, 29),
      I18n(
        "After 1.5 years of juggling work, studies, moving, wedding planning... WE'RE GETTING MARRIED!! Come celebrate with us!!",
        "Po roce a půl žonglování mezi prací, studiem, stěhováním a plánováním svatby... BEREME SE!! Přijďte to s námi oslavit!!",
        "1년 반 동안 일과 공부, 이사, 결혼 준비를 병행하다가... 드디어 결혼합니다!! 저희와 함께 축하해 주세요!!"
      ),
      "./images/dress-running.jpg"
    )
  )
