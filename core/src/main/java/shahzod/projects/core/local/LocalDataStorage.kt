package shahzod.projects.core.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import shahzod.projects.core.util.SharedPreferenceDelegate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataStorage @Inject constructor(@ApplicationContext context: Context) :
    SharedPreferenceDelegate(context) {
    var accessToken: String by Strings("")
    var refreshToken: String by Strings("")
    var isSigned: Boolean by Booleans(false)
    var language : String by Strings("uz")
}