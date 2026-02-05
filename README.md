# VKT4 - Navigointi & Jaettu ViewModel (Task Manager)

Tämä projekti laajentaa aiempaa tehtävienhallintasovellusta navigoinnilla ja jaetulla ViewModel-arkkitehtuurilla.

## Navigointi Jetpack Composessa
Sovelluksessa käytetään Jetpack Compose Navigation -kirjastoa näkymien väliseen siirtymiseen.
- **Single-Activity-arkkitehtuuri**: Sovelluksessa on vain yksi Activity (`MainActivity`), joka sisältää `NavHost`-komponentin.
- **NavController**: Hallitsee navigointitapahtumia ja ylläpitää takaisinkutsu-pinoa (back stack).
- **NavHost**: Määrittelee sovelluksen navigointikaavion (nav graph), jossa eri reitit (`home`, `calendar`, `settings`) on kytketty vastaaviin Composable-funktioihin.

## MVVM ja jaettu tila
Sovelluksessa hyödynnetään MVVM-mallia siten, että sama `TaskViewModel` jaetaan eri näkymien välillä.
- **Jaettu ViewModel**: `MainActivity` luo ViewModelin ja välittää sen kaikille sivuille. Tämä varmistaa, että sama tehtävälista ja tila ovat käytettävissä kaikkialla.
- **Reaktiivisuus**: Kun tehtävää muokataan HomeScreenillä, muutos päivittyy välittömästi myös CalendarScreenille, koska molemmat kuuntelevat samaa `StateFlow`-virtaa.

## Näkymät ja toiminnot
- **HomeScreen**: Perinteinen listanäkymä tehtävistä.
- **CalendarScreen**: Tehtävät on ryhmitelty päivämäärän mukaan (`groupBy`). Jokainen päivä toimii otsikkona, jonka alla ovat kyseisen päivän tehtävät.
- **SettingsScreen**: Dummy-asetussivu, jossa on toimiva teeman vaihto (Tumma/Vaalea).
- **AlertDialog**: Sekä uuden tehtävän lisäys että olemassa olevan muokkaus tapahtuvat dialogin kautta. Dialogi sisältää kentät otsikolle, kuvaukselle, prioriteetille ja päivämäärälle.

## Navigointirakenne
Navigointi on toteutettu `TopAppBar`-komponentin kautta, josta löytyy ikonit eri näkymille.
- **Home** (Lista-ikoni)
- **Calendar** (Kalenteri-ikoni)
- **Settings** (Asetus-ikoni)
- **Back-navigointi**: Järjestelmän back-painike toimii automaattisesti NavControllerin ansiosta.

## Videodemo
[Linkki YouTube-videoon]

## APK-tiedosto
Löytyy projektin `release`- tai `app/build/outputs/apk/debug` -kansiosta.
