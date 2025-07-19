// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.vkid.manifest.placeholders) apply true
}

vkidManifestPlaceholders {
    // Добавьте плейсхолдеры сокращенным способом. Например, vkidRedirectHost будет "vk.com", а vkidRedirectScheme будет "vk$clientId".
    init(
        clientId = "53916919",
        clientSecret = "Wj9InX6DUDSOCQR9zGqN",
    )
    // Или укажите значения явно через properties, если не хотите использовать плейсхолдеры.
    vkidRedirectHost = "vk.com"      // Обычно vk.com.
    vkidRedirectScheme = "vk53916919" // Строго в формате vk{ID приложения}.
    vkidClientId = "53916919"
    vkidClientSecret = "Wj9InX6DUDSOCQR9zGqN"
}