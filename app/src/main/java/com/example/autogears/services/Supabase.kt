package com.example.autogears.services

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object Supabase {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://dursbdtlewjqxkphvqkf.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR1cnNiZHRsZXdqcXhrcGh2cWtmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE2OTA0NDAsImV4cCI6MjA1NzI2NjQ0MH0.4oOTGnrJVzqNcVpCMhrehKA8HIXUz9tyB_hz7QULcb0"
    ) {
        install(Auth){
            flowType = FlowType.PKCE
        }
        install(Postgrest)
    }
}