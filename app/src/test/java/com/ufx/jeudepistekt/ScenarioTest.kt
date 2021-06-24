package com.ufx.jeudepistekt

import com.google.gson.GsonBuilder
import com.ufx.jeudepistekt.jeu.EtapElem
import com.ufx.jeudepistekt.jeu.Etape
import com.ufx.jeudepistekt.jeu.Scenario
import org.junit.Test
import java.io.File

import com.ufx.jeudepistekt.jeu.TYPE.TXT
import com.ufx.jeudepistekt.jeu.TYPE.IMG
import com.ufx.jeudepistekt.jeu.TYPE.QRC
import com.ufx.jeudepistekt.jeu.TYPE.VAR
import com.ufx.jeudepistekt.jeu.TYPE.BTN
import com.ufx.jeudepistekt.jeu.TYPE.EDT
import com.ufx.jeudepistekt.jeu.TYPE.ETP
import com.ufx.jeudepistekt.jeu.TYPE.LCK
import com.ufx.jeudepistekt.jeu.TYPE.UCK

class ScenarioTest {


    @Test
    fun buildJsonFile() {

        val e0 = Etape(0,listOf(
            EtapElem(TXT,"Bienvenue a vous, voyageurs. Vous etes les derniers apprentis de l'ordre des moines-guerriers Kai. Un terrible sorcier du nom de Vonotar s'est installé dans les terres reculées de Kalte, et vous seuls pouvez l'arreter. \\n Dans cette epreuve, l'art du Kai pourra vous etre utile, et votre maitre peut vous enseigner une technique avant de partir. Choisissez bien, et mettez vous en route. Une grande aventure vous attends !"),
            EtapElem(IMG,"e0i0"),
            EtapElem(QRC,"Skill_Camouflage"),
            EtapElem(QRC,"Skill_6eSens"),
            EtapElem(QRC,"Skill_Telekinesie"),
            ),
            mapOf(Pair("ETAPE1 : Direction",1)),
            listOf(Pair("Skill_Camouflage", listOf(
                EtapElem(TXT,"Vous avez finallement choisi d'apprendre le camouflage") ,
                EtapElem(VAR,"skill=1")
            )),Pair("Skill_6eSens", listOf(
                EtapElem(TXT,"Vous avez finallement choisi d'apprendre le 6e sens"),
                EtapElem(VAR,"skill=2")
            )),Pair("Skill_Telekinesie", listOf(
                EtapElem(TXT,"Vous avez finallement choisi d'apprendre la telekinesie"),
                EtapElem(VAR,"skill=3")
            )),
            )
        )

        val e1 = Etape(0,listOf(
            EtapElem(TXT,"Vous voila parti pour traverser la contrée gelée de Kalte. Le dernier village avant la contrée inospitalière est maintenant derrière vous. Le vent froid souffle doucement sur votre expedition."),
            EtapElem(IMG,"e1i0"),
            EtapElem(TXT,"Ici deux choix s'offrent à vous : vous pouvez passer par les montagnes directement au Nord, route dangereuse, mais plus courte, ou bien contourner par la vallée à l'Ouest, route plus sûre, mais plus longue. Une fois votre choix fait, vous pourrez vous remettre en route !")
        ), mapOf(
            Pair("ETAPE2 : Montage",2),
            Pair("ETAPE3 : Vallee",3)
        )
        )

        val e2 = Etape(2,listOf(
            EtapElem(VAR,"chemin=2"),
            EtapElem(IMG,"e2i0"),
            EtapElem(TXT,"Vous avancez prudement dans les montagnes depuis un jour sans encombre. La nuit commence à tomber, et vous decidez de monter le camp. Soudain retentit le hurlement d'un loup. Un autre lui répond. Et un troisième."),
            EtapElem(TXT,"Ils sont autours de vous ! Vite, trouvez les, avant qu'ils ne vous trouvent !"),
            EtapElem(QRC,"LoupA"),
            EtapElem(QRC,"LoupB"),
            EtapElem(QRC,"LoupC"),
            EtapElem(TXT,"Bravo, tout les loups sont deja en fuite ! Vous pouvez bivouaquer tranquille et reprendre la route ensuite.","loup == 0"),
            ),
            mapOf(Pair("ETAPE4 : Montage2",4)), listOf(
                Pair("LoupA", listOf(
                EtapElem(TXT,"Bravo, un loup à terre"),
                EtapElem(VAR,"loup=-=1"),
                EtapElem(TXT,"Bravo, tout les loups sont en fuite ! Vous pouvez bivouaquer tranquille et reprendre la route ensuite.","loup == 0")
                )
                ),
                Pair("LoupB", listOf(
                    EtapElem(TXT,"Bravo, un loup a pris la fuite"),
                    EtapElem(VAR,"loup=-=1"),
                    EtapElem(TXT,"Bravo, tout les loups sont neutralisés ! Vous pouvez bivouaquer tranquille et reprendre la route ensuite.","loup == 0")
                )
                ),
                Pair("LoupA", listOf(
                    EtapElem(TXT,"Bravo, un loup a été assommé"),
                    EtapElem(VAR,"loup=-=1"),
                    EtapElem(TXT,"Bravo, vous êtes hors de danger ! Vous pouvez bivouaquer tranquille et reprendre la route ensuite.","loup == 0")
                )
                )
        )
        )


        val e3 = Etape(3,listOf(
            EtapElem(VAR,"chemin=3"),
            EtapElem(IMG,"e3i0"),
            EtapElem(TXT,"Vous avez deja marché 2 jours dans la valée. L'air est froid et sec. Votre progression est rapide, mais le chemin a parcourir est encore long. Vous faites une pause pour reprendre votre souffle, et admirer le paysage. Les montagnes que vous contournez sont imposantes."),
            EtapElem(TXT,"Vous reprenez la route.")
        ),
            mapOf(Pair("ETAPE5 : Vallee2",5))
        )

        val e4 = Etape(4,listOf(
            EtapElem(TXT,"Vous avez passé le col à l'instant. Maintenant le chateau du sorcier est en vue, sur le flanc de la montagne que vous surplombez. Vous y serez avant la nuit !"),
            EtapElem(IMG,"e4i0")
            ),
            mapOf(Pair("ETAPE6 : Chateau",6))
        )

        val e5 = Etape(5,listOf(
            EtapElem(TXT,"Vous avancez à toute vitesse à l'entrée de la vallee. Le chateau apparait à l'horizon et à ce rythme, vous y serez avant la nuit. Tout à coup, le sol se dérobe sous vos pieds."),
            EtapElem(TXT,"La chute est courte. Quand vous vous relevez, vous êtes dans une grotte immense."),
            EtapElem(IMG,"e5i0"),
            EtapElem(TXT,"Vous ne perdez pas votre sang froid et avancez dans la grotte, en direction d'une lumière. Au fond, un tunnel étroit s'ouvre dans la paroi. Des coupes enflammés sont pendues au plafond, eclairant la route. Vous avancez sur ce chemin !")
        ),
            mapOf(Pair("ETAPE6 : Chateau",6))
        )

        val e6 = Etape(6,listOf(
            EtapElem(TXT,"Vous emergez enfin à l'air libre","chemin == 3"),
            EtapElem(IMG,"e6i0"),
            EtapElem(TXT,"Devant vous se trouve enfin le chateau du Sorcier. Votre voyage arrive à son but ! Il ne vous reste plus qu'a le trouver et le neutraliser. Rassemblez votre courage et entrez !")
            ),
            mapOf(Pair("ETAPE7 : Statue",7))
        )

        val e7 = Etape(7,listOf(
            EtapElem(TXT,"Vous passez la cour du chateau et entrez dans une grande salle. Au centre repose une statue qui semble avoir été sculptée dans de la pierre blanche et lisse. A gauche de la statue, un escalier monte dans l'ombre. Une porte est situé à l'oposée de la salle, de l'autre coté de la statue.") ,
            EtapElem(IMG,"e7i0"),
            EtapElem(BTN,"Monter l'escalier"),
            EtapElem(BTN,"Aller vers la porte"),
            EtapElem(BTN,"Tenter d'aller faire réagir la statue")
            ),
            mapOf(Pair("ETAPE8 : Tour",8)), listOf(
                Pair("Monter l'escalier", listOf(
                    EtapElem(TXT,"Vous vous avancez et commencez à monter l'escalier ...","tour == 0"),
                    EtapElem(TXT,"Vous changez d'avis et vous dirigez vers l'escalier ...","tour != 0"),
                    EtapElem(VAR,"tour=1")

                    )
                ),
                Pair("Aller vers la porte", listOf(
                    EtapElem(TXT,"Vous traversez la salle, passez à coté de la statue et poussez la porte ...","tour == 0"),
                    EtapElem(TXT,"Vous vous ravisez et traversez la salle en direction de la porte ...","tour != 0"),
                    EtapElem(VAR,"tour=2"),
                    )
                ),
                Pair("Tenter d'aller faire réagir la statue", listOf(
                    EtapElem(TXT,"Vous vous echinez sur la statue, mais elle ne bouge pas d'un millimètre. Si seulement vous maitrisiez la telekinesie ...","tour == 0 && skill != 3"),
                    EtapElem(TXT,"Vous revenez sur vos pas et explorez la statue. Elle ne bouge pas d'un millimètre. Si seulement vous maitrisiez la telekinesie ...","tour != 0 && skill != 3"),
                    EtapElem(TXT,"Vous vous approchez de la statue et etes pris d'une intuition. Vous vous concentrez fortement, et envoyez une décharge telekinésique vers la statue. Elle s'effondre sur le coté, libérant un passage que vous vous dépéchez d'emprunter...","tour == 0 && skill == 3"),
                    EtapElem(TXT,"Vous revenez au centre de la pièce et approchez de la statue. Vous etes pris d'une intuition. Vous vous concentrez fortement, et envoyez une décharge telekinésique vers la statue. Elle s'effondre sur le coté, libérant un passage que vous vous dépéchez d'emprunter...","tour != 0 && skill == 3"),
                    EtapElem(VAR,"tour=1"),
                    EtapElem(VAR,"tour=3","skill == 3")
                )
                ),
            )

        )

        val e8 = Etape(8,listOf(
            EtapElem(TXT,"Vous montez, encore et encore. L'escalier n'en finit pas. Les murs sont nus et sans fenetres. Au bout d'une eternite, vous débouchez sur un spectacle à couper le souffle.","tour=1") ,
            EtapElem(TXT,"Vous descendez des escaliers, en montez d'autres. Vous traversez des salles vides et des couloirs sombre. Finalement vous arrivez en d'une tour de guet en pierre. Vous ne prenez le temps d'admirer la vue et vous repartez par un autre chemin","tour=2") ,
            EtapElem(TXT,"Apres être descendu un court escalier de pierre vous arrivez près d'un cadre de pierre. L'air dans le cadre frémit legerement. Vous passez à travers la cadre, et d'un coup, le vent vous fouette le visage.","tour=3") ,
            EtapElem(IMG,"e8i0"),
            EtapElem(TXT,"Vous etes tout en haut d'une tour de guet. Une tas de bois gigantesque est présent. Si vous connaissez un sort de feu, vous pouvez tenter de l'enflammer, sinon vous repartez par un autre chemin.","tour=1") ,
            EtapElem(TXT,"Vous etes tout en haut d'une tour de guet. Une tas de bois gigantesque est présent. Vous repassez le portail pour prendre une torche dans le souterrain et enflammez le bois. La flamme qui s'ensuit doit se voire depuis le pays voisin. Elle a probablement une utilitée, mais vous n'avez pas le loisir de rester pour vérifier. Vous décidez de repartir par l'escalier qui se découpe au sol de la tour.","tour=3") ,
            EtapElem(VAR,"renfort=1","tour=3"),
            EtapElem(EDT,"Lancer un sort","tour=1",additional1 = "poussifeu")
            ),
            mapOf(Pair("ETAPE9 : Cachots",9)), listOf(
                Pair("Lancer un sort", listOf(
                    EtapElem(TXT,"Le bois s'enflamme d'un coup, et flamme atteind les nuages. Le plancher commence à s'embraser. Vous sortez de la tour en courant et empruntez un autre chemin") ,
                    EtapElem(VAR,"renfort=1")
                    )
                )
            )
        )

        val e9 = Etape(9,listOf(
            EtapElem(TXT,"Vous arrivez dans les cachots du chateau. Ca a l'air d'un cul de sac, mais il y a quatres portes de cellules. Vous decidez d'en ouvrir une !") ,
            EtapElem(IMG,"e9i0"),
            EtapElem(TXT,"Choisissez la porte que vous voulez ouvrir")
            ),mapOf(
            Pair("ETAPE10 : Cachot1",10),
            Pair("ETAPE11 : Cachot2",11),
            Pair("ETAPE12 : Cachot3",12),
            Pair("ETAPE13 : Cachot4",13),

            )
        )

        val e10 = Etape(10,listOf(
            EtapElem(TXT,"Un enorme monstre vous attends juste derriere la porte !"),
            EtapElem(IMG,"e10i0")
            ),mapOf(
            Pair("ETAPE9 : Cachots",9)
        )
        )

        val e11 = Etape(11,listOf(
            EtapElem(TXT,"Vous arrivez dans une pièce au centre de laquelle se dresse une étrange machine. La porte se referme d'un coup derrière vous !"),
            EtapElem(IMG,"e11i0")
        ),mapOf(
            Pair("ETAPE9 : Cachots",9)
        )
        )

        val e12 = Etape(12,listOf(
            EtapElem(LCK,"14"),
            EtapElem(TXT,"Vous entendez une voix derrière la porte. La voix demande s'il y a quelqu'un. Vous répondez, mais plus personne ne répond. Vous pouvez ouvrir la porte, ou alors retourner au centre des cachots pour tenter une autre porte") ,
            EtapElem(BTN,"Pousser le verrou et ouvrir")
        ),mapOf(
            Pair("ETAPE9 : Cachots",9),
            Pair("ETAPE14 : Cour",14)
        ),listOf(
            Pair("Pousser le verrou et ouvrir", listOf(
                EtapElem(IMG,"e12i1"),
                EtapElem(TXT,"Un vieil homme se trouve derrière la porte. En echange de la liberté, il est prêt à vous guider jusqu'au trésor du chateau. Vous le suivez donc !") ,
                EtapElem(VAR, "vieux=1"),
                EtapElem(UCK,"14")
            )
        ))
        )

        val e13 = Etape(13,listOf(
            EtapElem(TXT,"La porte s'ouvre sur une cellule immense, et completement vide. Il flotte encore une légère odeur de cochon grillé."),
            EtapElem(IMG,"e13i0"),
            EtapElem(TXT,"Rien à voir ici, revenez au centre à l'entrée des cachots pour ouvrir une autre porte."),
            EtapElem(VAR,"cochon=1")
            ),mapOf(
            Pair("ETAPE9 : Cachots",9)
        )
        )

        val e14 = Etape(14,listOf(
            EtapElem(TXT, "Restant sur vos garde, vous suivez le vieux bonhomme au travers de la forteresse. Un long moment s'ecoule, et après avoir poussé une lourde porte de bois, vous voila dans une cour intérieur, au pied du donjon central.") ,
            EtapElem(IMG,"e14i0"),
            EtapElem(TXT, "Vous entendez des bruits venant de l'entrée du donjon. Vous supposez qu'un groupe de soldats doit en garder l'entree. Vous pouvez vous préparer à passer en force, essayer de trouver une autre entrée ou tenter de vous camoufler.")
        ),mapOf(
            Pair("ETAPE15 : Bataille",15),
            Pair("ETAPE16 : Retraite",16),
            Pair("ETAPE17 : Camouflage",17)
        )
        )

        val e15 = Etape(15,listOf(
            EtapElem(LCK,"18"),
            EtapElem(TXT, "3 créatures infernales vous tombent dessus") ,
            EtapElem(IMG, "e15i0"),
            EtapElem(TXT, "Pendant que certain retiennent difficilement ces soldats du démon, le vieux vous tend un parchemin magique. Lancer le sort qu'il contient est votre seule chance de vous en sortir") ,
            EtapElem(IMG, "e15i1"),
            EtapElem(EDT,"Lancer un sort",additional1 = "LOSE MOMENTUM")
        ),mapOf(
            Pair("ETAPE18 : Donjon",18)
        ),listOf(
            Pair("Lancer un sort", listOf(
                EtapElem(TXT, "Les soldats infernaux sont immobilisés !") ,
                EtapElem(UCK,"18"),
                EtapElem(TXT, "Vous vous apercevez que le vieux a pris un coup mortel durant le combat. Il repose au sol, l'air tranquille. Le pauvre n'aura pas profité longtemps de la liberté. Vous entrez dans le donjon et commencez à monter.") ,
                EtapElem(VAR, "vieux=0")

                )
            )
        )
        )

        val e16 = Etape(15,listOf(
            EtapElem(TXT, "Bien joué ! Une porte derobee vous permet d'entrer. Il n'y a aucun garde ici. Prudement vous entrez dans le donjon et en entamez l'ascension.") ,
            EtapElem(IMG,"e14i0")

        ),mapOf(
            Pair("ETAPE18 : Donjon",15)
        )
        )

        val e17 = Etape(17,listOf(
            EtapElem(LCK,"18","skill != 1"),
            EtapElem(TXT, "Votre camouflage ne tient pas la route et les monstres vous reperent instantanément. Vous allez devoir vous battre !","skill != 1") ,
            EtapElem(BTN,"Engager le combat","skill != 1"),
            EtapElem(TXT, "Votre camouflage est parfait et vous passez devant 3 créatures infernales sans vous faire voir.","skill == 1"),
            EtapElem(IMG,"e15i1","skill == 1"),
            EtapElem(TXT, "Le vieux tremble, mais vous le tenez fermement pour qu'il ne fasse pas de bêtise. Une fois le son des soldats estompé, vous le lachez enfin, et entamez la montée du donjon.","skill == 1"),

        ),mapOf(
            Pair("ETAPE18 : Donjon",18)
        ),listOf(
            Pair("Engager le combat", listOf(
                EtapElem(ETP,"15")
            )
        )
        )
        )

        val e18 = Etape(18,listOf(
            EtapElem(TXT,"Vous montez en courant depuis de longues minutes, lorsque vous arrivez à un palier d'ou part un couloir enfumé, rempli de statuettes. Vous avancez dans le couloir. A son extremite, une issue est présente de chaque coté"),
            EtapElem(IMG,"e18i0"),
            EtapElem(TXT,"Le vieil homme vous fait signe que les deux chemins montent au sommet. Vous pouvez donc choir celui que vous voulez.","vieux == 1"),
            EtapElem(TXT,"Vous entendez des coups sourd venant de la gauche, comme des pas lourds et desordonnés"),
            EtapElem(TXT,"Votre 6e sens s'active soudain, comme un appel très fort. Vous savez que le bruit de pas du couloir gauche vous est familier. Mieux, il vous donne confiance.","skill == 2 && renfort == 1"),
            EtapElem(TXT,"Votre 6e sens s'active soudain, comme un appel très fort. Le bruit de pas du couloir gauche vous est extremement désagreable. Du genre qu'il faut éviter à tout prix.","skill == 2 && renfort != 1"),
            EtapElem(TXT,"Gauche ou droite ? Vous vous décidez et vous engagez dans l'issue choisie")

        ),mapOf(
            Pair("ETAPE19 : DonjonG",19),
            Pair("ETAPE20 : DonjonD",20)
        )
        )

        val e19 = Etape(19,listOf(
            EtapElem(TXT,"Vous montez les escaliers à gauche. Les pas sont de plus en plus fort. Enfin vous les apercevez. Il s'agit de grands guerriers revètus d'armure et lourdement armés."),
            EtapElem(IMG,"e19i0"),
            EtapElem(TXT,"L'un d'eux s'avance et vous gratifie d'un salut. Vous reconnaissez leur armure, qui est celle des soldats d'élite du comté à l'est de Kalte. Vous lui rendez son salut. Il a reconnu votre blason egalement, et vous indique qu'ils sont venu à cause d'un signal de la forme d'un feu qui a été allumé sur une des tours. Ils se joignent à votre troupe et vous reprenez l'ascension du donjon.","renfort == 1"),
            EtapElem(TXT,"En reprenant la marche, vous remarquez que le vieil homme a profité de la bousculade pour s'eclipser. Tant pis, vous allez devoir continuer sans lui","renfort == 1"),
            EtapElem(TXT,"Vous reconnaissez leurs armures comme celle de la milice du comté à l'est de Kalte, mais leur visage n'exprime plus rien. De toute évidence, ils sont envoûtés par le sorcier. Vous allez devoir vous défendre","renfort == 0"),
            EtapElem(VAR,"renfort = 2","renfort == 1")
            ),mapOf(
            Pair("ETAPE21 : ",21)
        ))

        val e20 = Etape(20,listOf(
            EtapElem(LCK,"21"),
            EtapElem(TXT,"Vous vous engagez dans l'escalier de droite. Mais tout en haut, une porte fermée bloque la voie. Il n'y a pas de serrure apparente. Il faut probablement lui dire quelque chose. Un étrange mur se trouve tout à coté :"),
            EtapElem(IMG,"e20i0"),
            EtapElem(EDT,"Dire",additional1 = "vampire")
        ),mapOf(
            Pair("ETAPE21 : Porte",21)
        ),listOf(
            Pair("Dire", listOf(
                EtapElem(TXT,"Bien joué ! La porte s'ouvre lentement. Vous vous avancez ..."),
                EtapElem(UCK,"21")
                )
            )
        )
        )

        val e21 = Etape(21,listOf(
            EtapElem(LCK,"22"),
            EtapElem(TXT,"Vous êtes enfin au sommet du donjon. Un grand tapis de velour rouge recouvre la pièce. Deux grandes fenêtres laissent aprecevoir l'horizon derrière la vallée enneigée."),
            EtapElem(TXT,"  Le vieil homme s'avance et vous dit 'Comme promis, je vais vous montrer ou se trouve le trésor'.\n Il s'approche d'un mur, et appuie dessus. Le mur coulisse. ","vieux == 1"),
            EtapElem(IMG,"e21i1","vieux == 1"),
            EtapElem(TXT,"Puis il pointe l'opposée de la grande salle : 'Le sorcier se trouve là !'","vieux == 1"),
            EtapElem(TXT,"Vous traversez prudement la salle rouge. Une porte immense vous fait face."),
            EtapElem(IMG,"e21i0"),
            EtapElem(TXT,"Cinq vers sont gravés sur le fronton : \n\nSans voix , il hurle,\nSans ailes , il voltige,\nSans dents , il mord\nSans bouche , il murmure,\nSans main, il caresse."),
            EtapElem(EDT,"Prononcer",additional1 = "vent",additional2 = "le vent")
        ),mapOf(
            Pair("ETAPE22 : Boss",22)
        ),listOf(
            Pair("Dire", listOf(
                EtapElem(TXT,"Un claquement se produit, les battants se séparent et la porte commence à s'ouvrir. Vous rassemblez votre courage et avancez vers un dernier combat."),
                EtapElem(UCK,"22")
            )
            )
        ))


        val e22 = Etape(22,listOf(
            EtapElem(LCK,"23"),
            EtapElem(TXT,"Vous vous avancez.Le sorcier est la, il incante devant un portail gigantesque. Vous pouvez sentir une aura maléfique qui s'en dégage."),
            EtapElem(IMG,"e22i0"),
            EtapElem(TXT,"Le sorcier se tourne vers vous. Il rit doucement en vous voyant : 'Vous avez du avoir du mal à arriver jusqu'ici, je reconnais votre bravoure. Mais c'est en vain ! Vous allez simplement périr les premiers'"),
            EtapElem(TXT,"Vous allez devoir livrer bataille pour vaincre le sorcier et fermer la porte"),
            EtapElem(TXT,"Heuresement les renforts que vous avez reçu vous donne un avantage indéniable","renfort == 2")
            ),mapOf(
            Pair("ETAPE23 : Fin",23)
        )
        )

        val e23 = Etape(23,listOf(
            EtapElem(TXT,"Victoire ! Le sorcier est vaincu. La menace est écarté. Le portail s'emballe et va exploser. Vous vous enfuyez du chateau et reprenez la route vers le Sud. Un vent de salvation souffle derrière vous."),
            EtapElem(IMG,"e23i0"),
            EtapElem(TXT,"Malgré la précipitation, vous avez réussi à emporter avec vous une grande partie du trésor. Vous etes victorieux sur tout les tableaux. Une grande vie vous attend.","vieux == 1"),
            EtapElem(IMG,"e21i1","vieux == 1"),
            EtapElem(TXT,"Longtemps on parlera de cette exploit que vous avez accompli. On se racontera cette aventure au coin du feu, on la jouera sur scene, on l'écrira et on la chantera.\nEt bientôt, très bientôt, vous serez loués pour bien d'autres aventures"),
            EtapElem(IMG,"e23i1"),
            ),mapOf())


        val etapes = listOf(e0,e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14,e15,e16,e17,e18,e19,e20,e21,e22,e23)

        val desc = "Venez bravez la froide contrée de Kalte pour sauver le monde d'un terrible sorcier"
        val cpr = "Scenario tiré du livre : 'Les grottes de Kalte' de la série 'Loup Solitaire' de Joe Dever\n Images du jeu 'The Elder Scrolls Online' de Bethesda SoftWorks"

        val s = Scenario("Kalte","fx",desc,cpr,etapes = etapes)
        s.variable["skill"] = 0
        s.variable["loup"] = 3
        s.variable["chemin"] = 0
        s.variable["tour"] = 0
        s.variable["renfort"] = 0
        s.variable["cochon"] = 0
        s.variable["vieux"] = 0



        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val jsonsPretty: String = gsonPretty.toJson(s)
        File("src/main/assets/ScenarioFile.json").writeText(jsonsPretty)

    }


}