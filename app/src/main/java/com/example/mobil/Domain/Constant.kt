package com.example.mobil.Domain

import android.net.http.HttpResponseCache.install
import com.google.android.gms.auth.api.Auth
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object Constant {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://herbpqodbbhlwieixmeo.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhlcmJwcW9kYmJobHdpZWl4bWVvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzk3NzcxODcsImV4cCI6MjA1NTM1MzE4N30.3p9c4foajNdTvHSZk69WpZsRdAJclb8kClGr2vb27lE"
    ) {
        install(Postgrest)
        install(Auth)
    }
}