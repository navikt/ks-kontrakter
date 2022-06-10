package no.nav.familie.kontrakter.ef.iverksett

import no.nav.familie.kontrakter.ef.felles.BehandlingType
import no.nav.familie.kontrakter.ef.felles.BehandlingÅrsak
import no.nav.familie.kontrakter.ef.felles.OpphørÅrsak
import no.nav.familie.kontrakter.ef.felles.RegelId
import no.nav.familie.kontrakter.ef.felles.Vedtaksresultat
import no.nav.familie.kontrakter.ef.felles.VilkårType
import no.nav.familie.kontrakter.ef.felles.Vilkårsresultat
import no.nav.familie.kontrakter.felles.ef.StønadType
import no.nav.familie.kontrakter.felles.tilbakekreving.Periode
import no.nav.familie.kontrakter.felles.tilbakekreving.Tilbakekrevingsvalg
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

sealed class IverksettDto {

    abstract val fagsak: FagsakdetaljerDto
    abstract val behandling: BehandlingsdetaljerDto
    abstract val søker: SøkerDto
    abstract val vedtak: VedtaksdetaljerDto
}

data class IverksettOvergangsstønadDto(
    override val fagsak: FagsakdetaljerDto,
    override val behandling: BehandlingsdetaljerDto,
    override val søker: SøkerDto,
    override val vedtak: VedtaksdetaljerOvergangsstønadDto,
) : IverksettDto()

data class IverksettBarnetilsynDto(
    override val fagsak: FagsakdetaljerDto,
    override val behandling: BehandlingsdetaljerDto,
    override val søker: SøkerDto,
    override val vedtak: VedtaksdetaljerBarnetilsynDto,
) : IverksettDto()

data class IverksettSkolepengerDto(
    override val fagsak: FagsakdetaljerDto,
    override val behandling: BehandlingsdetaljerDto,
    override val søker: SøkerDto,
    override val vedtak: VedtaksdetaljerSkolepengerDto,
) : IverksettDto()

data class SøkerDto(
    val personIdent: String,
    val barn: List<BarnDto> = emptyList(),
    val tilhørendeEnhet: String,
    val adressebeskyttelse: AdressebeskyttelseGradering? = null,
)

data class FagsakdetaljerDto(
    val fagsakId: UUID,
    val eksternId: Long,
    val stønadstype: StønadType
)

data class BehandlingsdetaljerDto(
    val behandlingId: UUID,
    val forrigeBehandlingId: UUID? = null,
    val eksternId: Long,
    val behandlingType: BehandlingType,
    val behandlingÅrsak: BehandlingÅrsak,
    val vilkårsvurderinger: List<VilkårsvurderingDto> = emptyList(),
    val aktivitetspliktInntrefferDato: LocalDate? = null,
    val kravMottatt: LocalDate? = null,
)

sealed class VedtaksdetaljerDto {

    abstract val resultat: Vedtaksresultat
    abstract val vedtakstidspunkt: LocalDateTime
    abstract val opphørÅrsak: OpphørÅrsak?
    abstract val saksbehandlerId: String
    abstract val beslutterId: String
    abstract val tilkjentYtelse: TilkjentYtelseDto?
    abstract val vedtaksperioder: List<VedtaksperiodeDto>
    abstract val tilbakekreving: TilbakekrevingDto?
    abstract val brevmottakere: List<Brevmottaker>
}

data class VedtaksdetaljerOvergangsstønadDto(
    override val resultat: Vedtaksresultat,
    override val vedtakstidspunkt: LocalDateTime,
    override val opphørÅrsak: OpphørÅrsak?,
    override val saksbehandlerId: String,
    override val beslutterId: String,
    override val tilkjentYtelse: TilkjentYtelseDto?,
    override val vedtaksperioder: List<VedtaksperiodeOvergangsstønadDto> = emptyList(),
    override val tilbakekreving: TilbakekrevingDto? = null,
    override val brevmottakere: List<Brevmottaker> = emptyList()
) : VedtaksdetaljerDto()

data class VedtaksdetaljerBarnetilsynDto(
    override val resultat: Vedtaksresultat,
    override val vedtakstidspunkt: LocalDateTime,
    override val opphørÅrsak: OpphørÅrsak?,
    override val saksbehandlerId: String,
    override val beslutterId: String,
    override val tilkjentYtelse: TilkjentYtelseDto?,
    override val vedtaksperioder: List<VedtaksperiodeBarnetilsynDto> = emptyList(),
    override val tilbakekreving: TilbakekrevingDto? = null,
    override val brevmottakere: List<Brevmottaker> = emptyList(),
    val kontantstøtte: List<PeriodeMedBeløpDto>,
    val tilleggsstønad: List<PeriodeMedBeløpDto>
) : VedtaksdetaljerDto()

data class VedtaksdetaljerSkolepengerDto(
    override val resultat: Vedtaksresultat,
    override val vedtakstidspunkt: LocalDateTime,
    override val opphørÅrsak: OpphørÅrsak?,
    override val saksbehandlerId: String,
    override val beslutterId: String,
    override val tilkjentYtelse: TilkjentYtelseDto?,
    override val vedtaksperioder: List<VedtaksperiodeSkolepengerDto> = emptyList(),
    override val tilbakekreving: TilbakekrevingDto? = null,
    override val brevmottakere: List<Brevmottaker> = emptyList()
) : VedtaksdetaljerDto()

data class VilkårsvurderingDto(
    val vilkårType: VilkårType,
    val resultat: Vilkårsresultat,
    val delvilkårsvurderinger: List<DelvilkårsvurderingDto> = emptyList()
)

data class DelvilkårsvurderingDto(
    val resultat: Vilkårsresultat,
    val vurderinger: List<VurderingDto> = emptyList()
)

data class VurderingDto(
    val regelId: RegelId,
    val svar: SvarId? = null,
    val begrunnelse: String? = null
)

sealed class VedtaksperiodeDto

data class VedtaksperiodeOvergangsstønadDto(
    val fraOgMed: LocalDate,
    val tilOgMed: LocalDate,
    val aktivitet: AktivitetType,
    val periodeType: VedtaksperiodeType
) : VedtaksperiodeDto()

data class VedtaksperiodeBarnetilsynDto(
    val fraOgMed: LocalDate,
    val tilOgMed: LocalDate,
    val utgifter: Int,
    val antallBarn: Int
) : VedtaksperiodeDto()

data class VedtaksperiodeSkolepengerDto(
    val perioder: List<DelårsperiodeSkoleårSkolepengerDto>,
    val utgiftsperioder: List<SkolepengerUtgiftDto>
) : VedtaksperiodeDto()

data class DelårsperiodeSkoleårSkolepengerDto(
    val studietype: SkolepengerStudietype,
    val fraOgMed: LocalDate,
    val tilOgMed: LocalDate,
    val studiebelastning: Int,
)

data class SkolepengerUtgiftDto(
    val utgiftstyper: Set<Utgiftstype>,
    val utgiftsdato: LocalDate,
    val utgifter: Int,
    val stønad: Int
)

enum class SkolepengerStudietype {
    HØGSKOLE_UNIVERSITET,
    VIDEREGÅENDE,
}

enum class Utgiftstype {
    SEMESTERAVGIFT,
    STUDIEAVGIFT,
    EKSAMENSAVGIFT
}

data class TilbakekrevingDto(
    val tilbakekrevingsvalg: Tilbakekrevingsvalg,
    val tilbakekrevingMedVarsel: TilbakekrevingMedVarselDto?
)

data class PeriodeMedBeløpDto(
    val fraOgMed: LocalDate,
    val tilOgMed: LocalDate,
    val beløp: Int
)

data class TilbakekrevingMedVarselDto(
    val varseltekst: String,
    val sumFeilutbetaling: BigDecimal? = null, // Hentes fra simulering hvis det mangler
    val perioder: List<Periode> = emptyList()
) // Hentes fra simulering hvis det mangler

enum class AdressebeskyttelseGradering {
    STRENGT_FORTROLIG,
    STRENGT_FORTROLIG_UTLAND,
    FORTROLIG,
    UGRADERT
}

enum class IverksettStatus {
    SENDT_TIL_OPPDRAG,
    FEILET_MOT_OPPDRAG,
    OK_MOT_OPPDRAG,
    JOURNALFØRT,
    OK,
    IKKE_PÅBEGYNT
}

enum class VedtaksperiodeType {
    MIGRERING,
    FORLENGELSE,
    HOVEDPERIODE,
    PERIODE_FØR_FØDSEL,
    UTVIDELSE,
    SANKSJON,
    NY_PERIODE_FOR_NYTT_BARN
}

enum class AktivitetType {
    MIGRERING,
    IKKE_AKTIVITETSPLIKT,
    BARN_UNDER_ETT_ÅR,
    FORSØRGER_I_ARBEID,
    FORSØRGER_I_UTDANNING,
    FORSØRGER_REELL_ARBEIDSSØKER,
    FORSØRGER_ETABLERER_VIRKSOMHET,
    BARNET_SÆRLIG_TILSYNSKREVENDE,
    FORSØRGER_MANGLER_TILSYNSORDNING,
    FORSØRGER_ER_SYK,
    BARNET_ER_SYKT,
    UTVIDELSE_FORSØRGER_I_UTDANNING,
    UTVIDELSE_BARNET_SÆRLIG_TILSYNSKREVENDE,
    FORLENGELSE_MIDLERTIDIG_SYKDOM,
    FORLENGELSE_STØNAD_PÅVENTE_ARBEID,
    FORLENGELSE_STØNAD_PÅVENTE_ARBEID_REELL_ARBEIDSSØKER,
    FORLENGELSE_STØNAD_PÅVENTE_OPPSTART_KVALIFISERINGSPROGRAM,
    FORLENGELSE_STØNAD_PÅVENTE_TILSYNSORDNING,
    FORLENGELSE_STØNAD_PÅVENTE_UTDANNING,
    FORLENGELSE_STØNAD_UT_SKOLEÅRET,
}

data class Brevmottaker(
    val ident: String,
    val navn: String,
    val mottakerRolle: MottakerRolle,
    val identType: IdentType
) {

    enum class MottakerRolle {
        BRUKER,
        VERGE,
        FULLMEKTIG
    }

    enum class IdentType {
        PERSONIDENT,
        ORGANISASJONSNUMMER
    }
}
