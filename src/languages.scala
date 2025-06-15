import scalatags.JsDom.all.*

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

val i18n = attr("data-i18n")

case class I18n(en: String, cs: String, kr: String):
  def c: String = this.hashCode().toString
  def i18nAttr = (i18n := c)


object I18n:
  var dynamicItems: Map[String, I18n] = Map() // ouch
  def apply(en: String, cs: String, kr: String): I18n =
    val res = new I18n(en, cs, kr)
    dynamicItems = dynamicItems + (res.c -> res)
    res
  def single(en: String): I18n = apply(en, en ,en)

enum Language:
  case CS, KR, EN
  case Other(name: String)
object Language:
  lazy val values = Set(CS, KR, EN)
  def fromString(value: String) = values
    .find(_.toString.toLowerCase == value.toLowerCase)
    .getOrElse(Other(value))

def detectedRegion(): Language = {
  val languageMap = Map(
    "cs" -> Language.CS,
    "cs-CZ" -> Language.CS,
    "ko" -> Language.KR,
    "ko-KR" -> Language.KR
  )

  val browserLang =
    js.Dynamic.global.navigator.language.asInstanceOf[String].toLowerCase()
  languageMap
    .find: (prefix, _) =>
      browserLang.startsWith(prefix)
    .map(_._2)
    .getOrElse(Language.EN)
}

// Language manager
@JSExportTopLevel("LanguageManager")
object LanguageManager {
  var currentLang = detectedRegion()

  @JSExportTopLevel("setLanguage")
  def setLanguage(lang: Language): Unit =
    currentLang = lang
    updatePageLanguage()

  @JSExportTopLevel("setLanguage2")
  def setLanguage(lang: Language.type => Language): Unit =
    setLanguage(lang(Language))

  @JSExportTopLevel("getText")
  def getText(key: String): String = {
    I18n.dynamicItems
      .get(key)
      .map: translation =>
        currentLang match
          case Language.EN => translation.en
          case Language.CS => translation.cs
          case Language.KR => translation.kr
          case Language.Other(otherLang) => translation.en // fallback to English
      .getOrElse(key)
  }
  private def updatePageLanguage(): Unit = {
    import org.scalajs.dom.document
    document.querySelectorAll(selectors = "[data-i18n]").foreach: node =>
      val key = node.getAttribute("data-i18n")
      node.textContent = getText(key)
  }
}
