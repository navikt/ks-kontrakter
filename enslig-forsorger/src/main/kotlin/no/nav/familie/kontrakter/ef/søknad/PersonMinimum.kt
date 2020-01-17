@file:ContextualSerialization(LocalDate::class)

package no.nav.familie.kontrakter.ef.søknad

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PersonMinimum(val navn: String,
                         val fødselsnummer: Fødselsnummer? = null,
                         val fødselsdato: LocalDate? = null,
                         val adresse: Adresse? = null,
                         val land: String? = null)
