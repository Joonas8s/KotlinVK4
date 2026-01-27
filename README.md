# VKT3 - MVVM & StateFlow (Task Manager)

Tämä projekti toteuttaa tehtävienhallinnan MVVM-arkkitehtuurilla.

## MVVM-arkkitehtuuri
MVVM (Model-View-ViewModel) erottaa käyttöliittymän, sovelluslogiikan ja datan toisistaan:
- **Model**: `Task.kt` määrittelee datan rakenteen.
- **ViewModel**: `TaskViewModel.kt` hallitsee sovelluksen tilaa ja logiikkaa. Se ei tiedä käyttöliittymän yksityiskohdista.
- **View**: `HomeScreen.kt` ja `DetailDialog` ovat Compose-komponentteja, jotka vain näyttävät ViewModelin tarjoamaa tilaa.

**Hyödyt Composessa:**
- UI on deklaratiivinen: se "piirtää itsensä" tilan perusteella. MVVM tarjoaa tämän tilan selkeästi.
- Helpompi testattavuus ja koodin ylläpito, kun logiikka ei ole näkymätiedostoissa.

## StateFlow
Sovellus käyttää `StateFlow`-rajapintaa tilan hallintaan.
- `StateFlow` on reaktiivinen datavirta, joka säilyttää aina uusimman tilan.
- Kun ViewModelissa olevaan listaan tehdään muutoksia (`addTask`, `updateTask` jne.), `StateFlow` ilmoittaa tästä kaikille kuuntelijoille.
- UI:ssa käytetään `collectAsState()`-funktiota, joka muuttaa Flow'n Compose-tilaksi, aiheuttaen näkymän automaattisen uudelleenhahmonnuksen (recomposition) heti kun data muuttuu.

## Toiminnot
- **Listaus**: Kaikki tehtävät listassa.
- **Lisäys**: FAB-painike avaa dialogin uuden tehtävän luontiin.
- **Muokkaus**: Tehtävän kynä-ikoni avaa muokkausdialogin.
- **Poisto**: Roskakori-ikoni poistaa tehtävän.
- **Toggle**: Checkbox vaihtaa suoritustilaa reaktiivisesti.
