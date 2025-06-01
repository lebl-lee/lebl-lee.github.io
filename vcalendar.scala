import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.URIUtils

case class InviteCalendar(
    begin: Date,
    end: Date,
    summary: String,
    description: String,
    location: String
) {
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

  def link =
    s"data:text/calendar;charset=utf-8,${URIUtils.encodeURIComponent(data)}"
}
