package com.panostob.mycourses.util.ext

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale

internal fun Context.updateLocale(lang: String): ContextWrapper {
    val configuration: Configuration = resources.configuration
    val localeToSwitchTo = Locale.forLanguageTag(lang)

    configuration.setLocale(localeToSwitchTo)
    val localeList = LocaleList(localeToSwitchTo)
    LocaleList.setDefault(localeList)
    configuration.setLocales(localeList)
    return ContextWrapper(createConfigurationContext(configuration))
}