//> using scala "3.6.1"
//> using platform scala-js
//> using dep "org.scala-js::scalajs-dom::2.8.0"
//> using dep "com.lihaoyi::scalatags::0.13.1"
//> using jsModuleKind "es"

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.*

import scala.scalajs.js.annotation.JSExportTopLevel
import scalatags.JsDom.all.*
import scalatags.JsDom.svgAttrs.viewBox
import scalatags.JsDom.{svgAttrs, svgTags}
import scalatags.JsDom.tags2.{nav, section}

import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.URIUtils

// Scala.js
@main def WeddingInvite(): Unit = {
  val lang = "en"
  // script https://cdn.tailwindcss.com
  val navBar = nav(cls := "bg-white shadow-md sticky top-0 z-50")(
    div(cls := "max-w-6xl mx-auto px-4 py-3 flex justify-between items-center")(
      span(cls := "text-xl font-semibold text-pink-600 script-font")(
        "David & Suhyoon"
      ),
      ul(cls := "flex space-x-4 text-gray-700")(
        li(a(href := "#invite", cls := "hover:text-pink-600")("Invite")),
        li(a(href := "#our-story", cls := "hover:text-pink-600")("Our Story")),
        li(a(href := "#phonebook", cls := "hover:text-pink-600")("Photobook")),
        li(a(href := "#schedule", cls := "hover:text-pink-600")("Calendar")),
        li(
          a(href := "#rsvp", cls := "hover:text-pink-600 font-semibold")("RSVP")
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
          )("Our Story"),
          // Right button - Photobook
          a(
            href := "./photobook.pdf",
            cls := "px-6 py-2 border-2 border-white text-white rounded-full " +
                  "backdrop-blur-sm bg-white/10 hover:bg-white/30 transition-all " +
                  "font-medium text-sm uppercase tracking-wider",
          )("Photobook")
        ),
        // Gradient overlay for bottom text
        div(
          cls := "absolute inset-x-0 bottom-0 h-48 bg-gradient-to-t from-black/40 to-transparent"
        ),
        // Content container
        div(
          cls := "relative z-10 px-4 pb-16"
        )(
          h1(
            cls := "text-6xl script-font text-white"
          )("David & Suhyoon"),
          p(
            cls := "text-2xl mt-4 text-white"
          )("Are Getting Married!")
        )
      )
    )
  )

  // Add this countdown section after the headerSection:
  val countdownSection = section(cls := "py-6 text-center bg-pink-50")(
    h3(cls := "text-2xl mb-4 text-gray-800")(
      "Counting down to our special day"
    ),
    div(cls := "grid grid-cols-4 max-w-2xl mx-auto gap-4")(
      div(cls := "bg-white p-4 rounded-lg shadow-sm")(
        div(id := "days", cls := "text-3xl font-bold text-pink-600")("0"),
        div(cls := "text-gray-600")("Days")
      ),
      div(cls := "bg-white p-4 rounded-lg shadow-sm")(
        div(id := "hours", cls := "text-3xl font-bold text-pink-600")("0"),
        div(cls := "text-gray-600")("Hours")
      ),
      div(cls := "bg-white p-4 rounded-lg shadow-sm")(
        div(id := "minutes", cls := "text-3xl font-bold text-pink-600")("0"),
        div(cls := "text-gray-600")("Minutes")
      ),
      div(cls := "bg-white p-4 rounded-lg shadow-sm")(
        div(id := "seconds", cls := "text-3xl font-bold text-pink-600")("0"),
        div(cls := "text-gray-600")("Seconds")
      )
    )
  )

  // Add this near the top of the file with other val definitions
  val weddingDate =
    new Date(2025, 7, 29, 15) // Month is 0-based, so 8 is August
  val weddingDateFormatted = s"August 29, 2025"

  val inviteText = section(cls := "bg-white text-center py-8 px-4")(
    p(cls := "text-lg max-w-2xl mx-auto mb-8")(
      "We invite you to celebrate our wedding day with us on ",
      strong(weddingDateFormatted),
      " at the beautiful ",
      strong(
        "Altán Grebovka, Havlíckovy sady 1369, 120 00 Praha 2-Vinohrady, Czechia"
      ),
      "."
    ),
    // Action buttons container
    div(cls := "max-w-2xl mx-auto mb-8")(
      // Add to Calendar button
      a(
        href := s"data:text/calendar;charset=utf-8,${URIUtils.encodeURIComponent(
          s"""BEGIN:VCALENDAR
             |VERSION:2.0
             |BEGIN:VEVENT
             |DTSTART:20250929T150000
             |DTEND:20250929T230000
             |SUMMARY:David & Suhyoon's Wedding
             |DESCRIPTION:We're getting married! Join us for our special day.
             |LOCATION:Altán Grebovka, Havlíckovy sady 1369, 120 00 Praha 2-Vinohrady, Czechia
             |END:VEVENT
             |END:VCALENDAR""".stripMargin
        )}",
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
    ),
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
            h3(cls := "text-lg font-semibold text-gray-800 mb-2")("Altán Grébovka"),
            p(cls := "text-gray-600 text-sm")(
              "Havlíčkovy sady 1369, 120 00 Praha 2-Vinohrady, Czechia"
            )
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
        form(id := "rsvpForm", cls := "space-y-4")(
          input(
            `type` := "text",
            placeholder := "Your Full Name",
            required := true,
            cls := "w-full border border-gray-300 p-3 rounded"
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
            cls := "bg-pink-500 hover:bg-pink-600 text-white px-6 py-2 rounded w-full"
          )("Submit RSVP"),
          p(
            id := "rsvpMessage",
            cls := "text-green-600 text-center mt-4 hidden"
          )("Thank you for your RSVP!")
        ),
        // Guest List Stats
        div(cls := "mt-6 p-4 bg-gray-50 rounded-lg")(
          div(cls := "text-center space-y-4")(
            div(
              span(id := "confirmedCount", cls := "text-3xl font-bold text-pink-600")("20"),
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
        cls := "text-3xl font-semibold text-center mb-8 script-font text-pink-600"
      )("Wedding Schedule"),
      div(cls := "max-w-5xl mx-auto grid md:grid-cols-2 gap-8 items-start")(
        // Left side: Schedule
        div(cls := "space-y-8")(
          // Pre-wedding day schedule
          div(
            h3(cls := "text-2xl font-semibold mb-6 text-gray-800")(
              "August 28 - Pre-Wedding"
            ),
            div(cls := "space-y-6")(
              div(cls := "flex items-center space-x-4")(
                div(cls := "text-right w-24 font-semibold text-pink-600")(
                  "11:00"
                ),
                div(cls := "w-px h-12 bg-pink-300"),
                div(cls := "flex-1")(
                  p(cls := "font-medium")("Welcome WORKOUT"),
                  p(cls := "text-gray-600 text-sm")(
                    "@ F45 Prague"
                  )
                )
              ),
              div(cls := "flex items-center space-x-4")(
                div(cls := "text-right w-24 font-semibold text-pink-600")(
                  "13:00"
                ),
                div(cls := "w-px h-12 bg-pink-300"),
                div(cls := "flex-1")(
                  p(cls := "font-medium")("Welcome DRINKS"),
                  p(cls := "text-gray-600 text-sm")("@ Cerveny Jelen"),
                  p(cls := "text-gray-600 text-sm")("Include RED in your outfit for free pilsners & bar treats from us :)")
                )
              )
            )
          ),

          // Wedding day schedule
          div(cls := "mt-10")(
            h3(cls := "text-2xl font-semibold mb-6 text-gray-800")(
              s"August 29 - Wedding Day"
            ),
            div(cls := "space-y-6")(
              div(cls := "flex items-center space-x-4")(
                div(cls := "text-right w-24 font-semibold text-pink-600")(
                  "14:15"
                ),
                div(cls := "w-px h-12 bg-pink-300"),
                div(cls := "flex-1")(
                  p(cls := "font-medium")("Reception"),
                  p(cls := "text-gray-600 text-sm")(
                    "Welcome drinks in cafe area"
                  )
                )
              ),
              div(cls := "flex items-center space-x-4")(
                div(cls := "text-right w-24 font-semibold text-pink-600")(
                  "15:00"
                ),
                div(cls := "w-px h-12 bg-pink-300"),
                div(cls := "flex-1")(
                  p(cls := "font-medium")("Ceremony"),
                  p(cls := "text-gray-600 text-sm")("Guest seated by 14:45 in upstairs pavillion"),
                  p(cls := "text-gray-600 text-sm")("15:30 Bestie BLESSINGs"),
                  p(cls := "text-gray-600 text-sm")("15:45 Round of Ribbons"),
                  p(cls := "text-gray-600 text-sm")("16:00 Group PHOTOSHOOT")

                )
              ),
              div(cls := "flex items-center space-x-4")(
                div(cls := "text-right w-24 font-semibold text-pink-600")(
                  "17:00"
                ),
                div(cls := "w-px h-12 bg-pink-300"),
                div(cls := "flex-1")(
                  p(cls := "font-medium")("Dinner"),
                  p(cls := "text-gray-600 text-sm")("opened with toast by parents")
                )
              ),
              div(cls := "flex items-center space-x-4")(
                div(cls := "text-right w-24 font-semibold text-pink-600")(
                  "18:30"
                ),
                div(cls := "w-px h-12 bg-pink-300"),
                div(cls := "flex-1")(
                  p(cls := "font-medium")("Cake CUTTING")
                )
              ),
              div(cls := "flex items-center space-x-4")(
                div(cls := "text-right w-24 font-semibold text-pink-600")(
                  "19:00"
                ),
                div(cls := "w-px h-12 bg-pink-300"),
                div(cls := "flex-1")(
                  p(cls := "font-medium")("Dessert & DJ Set")
                )
              ),
              div(cls := "flex items-center space-x-4")(
                div(cls := "text-right w-24 font-semibold text-pink-600")(
                  "20:00"
                ),
                div(cls := "w-px h-12 bg-pink-300"),
                div(cls := "flex-1")(
                  p(cls := "font-medium")("After Party"),
                  p(cls := "text-gray-600 text-sm")("Dancing and celebrations"),
                  p(cls := "text-gray-600 text-sm")("20:00 Sister Act"),
                  p(cls := "text-gray-600 text-sm")("20:30 Speed Luiz"),
                  p(cls := "text-gray-600 text-sm")("21:00 Surprise Performance by Bride & Groom"),
                )
              ),
              div(cls := "flex items-center space-x-4")(
                div(cls := "text-right w-24 font-semibold text-pink-600")(
                  "22:00"
                ),
                div(cls := "w-px h-12 bg-pink-300"),
                div(cls := "flex-1")(
                  p(cls := "font-medium")("Farewell"),
                  p(cls := "text-gray-600 text-sm")("Music close at 10 PM, venue open until 11:30 PM for last goodbyes"),
                )
              )
            )
          )
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
    previewLinksSection,
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
    div(cls := "absolute inset-0 bg-black/40"), // Overlay for better text visibility
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
  div(cls := s"flex flex-col ${if (reverse) "md:flex-row-reverse" else "md:flex-row"} items-center gap-8")(
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
