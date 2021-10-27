package com.ufx.jeudepistekt

import com.ufx.jeudepistekt.jeu.Stage
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.element.StageElement
import org.junit.jupiter.api.Test

class ScenarioKalteTest {
/*
    //Conditions au pifs : a reverifier
    @Test
    fun ScenarioKalte(){
        val e0 = Stage(0,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Bienvenue a vous, voyageurs. Vous êtes les derniers apprentis de l'ordre des moines-guerriers Kai. Un terrible sorcier du nom de Vonotar s'est installé dans les terres reculées de Kalte, et vous seuls pouvez l'arrêter. \n Dans cette épreuve, l'art du Kai pourra vous être utile, et votre maitre peut vous enseigner une technique avant de partir. Choisissez bien, et mettez vous en route. Une grande aventure vous attends !"),
            StageElement(StageElement.Companion.TYPE.IMG,"e0i0"),
            StageElement(StageElement.Companion.TYPE.QRC,"Skill_Camouflage",additional= arrayOf("0")),
            StageElement(StageElement.Companion.TYPE.QRC,"Skill_6eSens",additional= arrayOf("1")),
            StageElement(StageElement.Companion.TYPE.QRC,"Skill_Telekinesie",additional= arrayOf("2")),
        ),
            mapOf(Pair("ETAPE1 : Direction",1)),
            listOf(listOf(
                StageElement(StageElement.Companion.TYPE.TXT,"Vous avez finalement choisi d'apprendre le camouflage") ,
                StageElement(StageElement.Companion.TYPE.VAR,"skill=1")
            ),listOf(
                StageElement(StageElement.Companion.TYPE.TXT,"Vous avez finalement choisi d'apprendre le 6e sens"),
                StageElement(StageElement.Companion.TYPE.VAR,"skill=2")
            ), listOf(
                StageElement(StageElement.Companion.TYPE.TXT,"Vous avez finalement choisi d'apprendre la télékinésie"),
                StageElement(StageElement.Companion.TYPE.VAR,"skill=3")
            )
            )
        )

        val e1 = Stage(0,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous voila parti pour traverser la contrée gelée de Kalte. Le dernier village avant la contrée inhospitalière est maintenant derrière vous. Le vent froid souffle doucement sur votre expedition."),
            StageElement(StageElement.Companion.TYPE.IMG,"e1i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Ici deux choix s'offrent à vous : vous pouvez passer par les montagnes directement au Nord, route dangereuse, mais plus courte, ou bien contourner par la vallée à l'Ouest, route plus sûre, mais plus longue. Une fois votre choix fait, vous pourrez vous remettre en route !")
        ), mapOf(
            Pair("ETAPE2 : Montage",2),
            Pair("ETAPE3 : Vallee",3)
        )
        )

        val e2 = Stage(2,listOf(
            StageElement(StageElement.Companion.TYPE.VAR,"chemin=2"),
            StageElement(StageElement.Companion.TYPE.IMG,"e2i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous avancez prudemment dans les montagnes depuis un jour sans encombre. La nuit commence à tomber, et vous décidez de monter le camp. Soudain retentit le hurlement d'un loup. Un autre lui répond. Et un troisième."),
            StageElement(StageElement.Companion.TYPE.TXT,"Ils sont autour de vous ! Vite, trouvez les, avant qu'ils ne vous trouvent !"),
            StageElement(StageElement.Companion.TYPE.QRC,"LoupA"),
            StageElement(StageElement.Companion.TYPE.QRC,"LoupB"),
            StageElement(StageElement.Companion.TYPE.QRC,"LoupC"),
            StageElement(StageElement.Companion.TYPE.TXT,"Bravo, tout les loups sont deja en fuite ! Vous pouvez bivouaquer tranquille et reprendre la route ensuite.",condition = "loup == 0"),
        ),
            mapOf(Pair("ETAPE4 : Montage2",4)), listOf(
                listOf(
                    StageElement(StageElement.Companion.TYPE.TXT,"Bravo, un loup à terre"),
                    StageElement(StageElement.Companion.TYPE.VAR,"loup=-=1"),
                    StageElement(StageElement.Companion.TYPE.TXT,"Bravo, tout les loups sont en fuite ! Vous pouvez bivouaquer tranquille et reprendre la route ensuite.",condition = "loup == 0")
                ), listOf(
                    StageElement(StageElement.Companion.TYPE.TXT,"Bravo, un loup a pris la fuite"),
                    StageElement(StageElement.Companion.TYPE.VAR,"loup=-=1"),
                    StageElement(StageElement.Companion.TYPE.TXT,"Bravo, tout les loups sont neutralisés ! Vous pouvez bivouaquer tranquille et reprendre la route ensuite.",condition = "loup == 0")
                ), listOf(
                    StageElement(StageElement.Companion.TYPE.TXT,"Bravo, un loup a été assommé"),
                    StageElement(StageElement.Companion.TYPE.VAR,"loup=-=1"),
                    StageElement(StageElement.Companion.TYPE.TXT,"Bravo, vous êtes hors de danger ! Vous pouvez bivouaquer tranquille et reprendre la route ensuite.",condition = "loup == 0")
                )

            )
        )


        val e3 = Stage(3,listOf(
            StageElement(StageElement.Companion.TYPE.VAR,"chemin=3"),
            StageElement(StageElement.Companion.TYPE.IMG,"e3i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous avez deja marché 2 jours dans la vallée. L'air est froid et sec. Votre progression est rapide, mais le chemin a parcourir est encore long. Vous faites une pause pour reprendre votre souffle, et admirer le paysage. Les montagnes que vous contournez sont imposantes."),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous reprenez la route.")
        ),
            mapOf(Pair("ETAPE5 : Vallee2",5))
        )

        val e4 = Stage(4,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous avez passé le col à l'instant. Maintenant le chateau du sorcier est en vue, sur le flanc de la montagne que vous surplombez. Vous y serez avant la nuit !"),
            StageElement(StageElement.Companion.TYPE.IMG,"e4i0")
        ),
            mapOf(Pair("ETAPE6 : Chateau",6))
        )

        val e5 = Stage(5,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous avancez à toute vitesse à l'entrée de la vallee. Le chateau apparaît à l'horizon et à ce rythme, vous y serez avant la nuit. Tout à coup, le sol se dérobe sous vos pieds."),
            StageElement(StageElement.Companion.TYPE.TXT,"La chute est courte. Quand vous vous relevez, vous êtes dans une grotte immense."),
            StageElement(StageElement.Companion.TYPE.IMG,"e5i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous ne perdez pas votre sang froid et avancez dans la grotte, en direction d'une lumière. Au fond, un tunnel étroit s'ouvre dans la paroi. Des coupes enflammés sont pendues au plafond, éclairant la route. Vous avancez sur ce chemin !")
        ),
            mapOf(Pair("ETAPE6 : Chateau",6))
        )

        val e6 = Stage(6,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous émergez enfin à l'air libre",condition = "chemin == 3"),
            StageElement(StageElement.Companion.TYPE.IMG,"e6i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Devant vous se trouve enfin le chateau du Sorcier. Votre voyage arrive à son but ! Il ne vous reste plus qu'a le trouver et le neutraliser. Rassemblez votre courage et entrez !")
        ),
            mapOf(Pair("ETAPE7 : Statue",7))
        )

        val e7 = Stage(7,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous passez la cour du chateau et entrez dans une grande salle. Au centre repose une statue qui semble avoir été sculptée dans de la pierre blanche et lisse. A gauche de la statue, un escalier monte dans l'ombre. Une porte est situé à l'opposé de la salle, de l'autre coté de la statue.") ,
            StageElement(StageElement.Companion.TYPE.IMG,"e7i0"),
            StageElement(StageElement.Companion.TYPE.BTN,"Monter l'escalier"),
            StageElement(StageElement.Companion.TYPE.BTN,"Aller vers la porte"),
            StageElement(StageElement.Companion.TYPE.BTN,"Tenter d'aller faire réagir la statue")
        ),
            mapOf(Pair("ETAPE8 : Tour",8)), listOf(
                listOf(
                    StageElement(StageElement.Companion.TYPE.TXT,"Vous vous avancez et commencez à monter l'escalier ...",condition = "tour == 0"),
                    StageElement(StageElement.Companion.TYPE.TXT,"Vous changez d'avis et vous dirigez vers l'escalier ...",condition = "tour != 0"),
                    StageElement(StageElement.Companion.TYPE.VAR,"tour=1")

                ), listOf(
                    StageElement(StageElement.Companion.TYPE.TXT,"Vous traversez la salle, passez à coté de la statue et poussez la porte ...",condition = "tour == 0"),
                    StageElement(StageElement.Companion.TYPE.TXT,"Vous vous ravisez et traversez la salle en direction de la porte ...",condition = "tour != 0"),
                    StageElement(StageElement.Companion.TYPE.VAR,"tour=2"),
                ), listOf(
                    StageElement(StageElement.Companion.TYPE.TXT,"Vous vous échinez sur la statue, mais elle ne bouge pas d'un millimètre. Si seulement vous maîtrisiez la télékinésie ...",condition = "tour == 0 && skill != 3"),
                    StageElement(StageElement.Companion.TYPE.TXT,"Vous revenez sur vos pas et explorez la statue. Elle ne bouge pas d'un millimètre. Si seulement vous maîtrisiez la télékinésie ...",condition = "tour != 0 && skill != 3"),
                    StageElement(StageElement.Companion.TYPE.TXT,"Vous vous approchez de la statue et êtes pris d'une intuition. Vous vous concentrez fortement, et envoyez une décharge télékinésique vers la statue. Elle s'effondre sur le coté, libérant un passage que vous vous dépêchez d'emprunter...",condition = "tour == 0 && skill == 3"),
                    StageElement(StageElement.Companion.TYPE.TXT,"Vous revenez au centre de la pièce et approchez de la statue. Vous êtes pris d'une intuition. Vous vous concentrez fortement, et envoyez une décharge télékinésique vers la statue. Elle s'effondre sur le coté, libérant un passage que vous vous dépêchez d'emprunter...",condition = "tour != 0 && skill == 3"),
                    StageElement(StageElement.Companion.TYPE.VAR,"tour=1"),
                    StageElement(StageElement.Companion.TYPE.VAR,"tour=3",condition = "skill == 3")
                )

            )

        )

        val e8 = Stage(8,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous montez, encore et encore. L'escalier n'en finit pas. Les murs sont nus et sans fenêtres. Au bout d'une éternité, vous débouchez sur un spectacle à couper le souffle.",condition = "tour=1") ,
            StageElement(StageElement.Companion.TYPE.TXT,"Vous descendez des escaliers, en montez d'autres. Vous traversez des salles vides et des couloirs sombre. Finalement vous arrivez en d'une tour de guet en pierre. Vous ne prenez le temps d'admirer la vue et vous repartez par un autre chemin",condition = "tour=2") ,
            StageElement(StageElement.Companion.TYPE.TXT,"Apres être descendu un court escalier de pierre vous arrivez près d'un cadre de pierre. L'air dans le cadre frémit légèrement. Vous passez à travers la cadre, et d'un coup, le vent vous fouette le visage.",condition = "tour=3") ,
            StageElement(StageElement.Companion.TYPE.IMG,"e8i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous êtes tout en haut d'une tour de guet. Une tas de bois gigantesque est présent. Si vous connaissez un sort de feu, vous pouvez tenter de l'enflammer, sinon vous repartez par un autre chemin.",condition = "tour=1") ,
            StageElement(StageElement.Companion.TYPE.TXT,"Vous êtes tout en haut d'une tour de guet. Une tas de bois gigantesque est présent. Vous repassez le portail pour prendre une torche dans le souterrain et enflammez le bois. La flamme qui s'ensuit doit se voire depuis le pays voisin. Elle a probablement une utilité, mais vous n'avez pas le loisir de rester pour vérifier. Vous décidez de repartir par l'escalier qui se découpe au sol de la tour.",condition = "tour=3") ,
            StageElement(StageElement.Companion.TYPE.VAR,"renfort=1",condition = "tour=3"),
            StageElement(StageElement.Companion.TYPE.EDT,"Lancer un sort",additional = arrayOf("tour=1","poussifeu"))
        ),
            mapOf(Pair("ETAPE9 : Cachots",9)), listOf(
                listOf(
                    StageElement(StageElement.Companion.TYPE.TXT,"Le bois s'enflamme d'un coup, et flamme atteint les nuages. Le plancher commence à s'embraser. Vous sortez de la tour en courant et empruntez un autre chemin") ,
                    StageElement(StageElement.Companion.TYPE.VAR,"renfort=1")
                )
            )
        )

        val e9 = Stage(9,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous arrivez dans les cachots du chateau. Ca a l'air d'un cul de sac, mais il y a quatre portes de cellules. Vous décidez d'en ouvrir une !") ,
            StageElement(StageElement.Companion.TYPE.IMG,"e9i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Choisissez la porte que vous voulez ouvrir")
        ),mapOf(
            Pair("ETAPE10 : Cachot1",10),
            Pair("ETAPE11 : Cachot2",11),
            Pair("ETAPE12 : Cachot3",12),
            Pair("ETAPE13 : Cachot4",13),

            )
        )

        val e10 = Stage(10,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Un énorme monstre vous attends juste derriere la porte !"),
            StageElement(StageElement.Companion.TYPE.IMG,"e10i0")
        ),mapOf(
            Pair("ETAPE9 : Cachots",9)
        )
        )

        val e11 = Stage(11,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous arrivez dans une pièce au centre de laquelle se dresse une étrange machine. La porte se referme d'un coup derrière vous !"),
            StageElement(StageElement.Companion.TYPE.IMG,"e11i0")
        ),mapOf(
            Pair("ETAPE9 : Cachots",9)
        )
        )

        val e12 = Stage(12,listOf(
            StageElement(StageElement.Companion.TYPE.LCK,"14"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous entendez une voix derrière la porte. La voix demande s'il y a quelqu'un. Vous répondez, mais plus personne ne répond. Vous pouvez ouvrir la porte, ou alors retourner au centre des cachots pour tenter une autre porte") ,
            StageElement(StageElement.Companion.TYPE.BTN,"Pousser le verrou et ouvrir")
        ),mapOf(
            Pair("ETAPE9 : Cachots",9),
            Pair("ETAPE14 : Cour",14)
        ),listOf(
            listOf(
                StageElement(StageElement.Companion.TYPE.IMG,"e12i1"),
                StageElement(StageElement.Companion.TYPE.TXT,"Un vieil homme se trouve derrière la porte. En échange de la liberté, il est prêt à vous guider jusqu'au trésor du chateau. Vous le suivez donc !") ,
                StageElement(StageElement.Companion.TYPE.VAR, "vieux=1"),
                StageElement(StageElement.Companion.TYPE.UCK,"14")
            )
        )
        )

        val e13 = Stage(13,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"La porte s'ouvre sur une cellule immense, et complètement vide. Il flotte encore une légère odeur de cochon grillé."),
            StageElement(StageElement.Companion.TYPE.IMG,"e13i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Rien à voir ici, revenez au centre à l'entrée des cachots pour ouvrir une autre porte."),
            StageElement(StageElement.Companion.TYPE.VAR,"cochon=1")
        ),mapOf(
            Pair("ETAPE9 : Cachots",9)
        )
        )

        val e14 = Stage(14,listOf(
            StageElement(StageElement.Companion.TYPE.TXT, "Restant sur vos garde, vous suivez le vieux bonhomme au travers de la forteresse. Un long moment s'écoule, et après avoir poussé une lourde porte de bois, vous voila dans une cour intérieur, au pied du donjon central.") ,
            StageElement(StageElement.Companion.TYPE.IMG,"e14i0"),
            StageElement(StageElement.Companion.TYPE.TXT, "Vous entendez des bruits venant de l'entrée du donjon. Vous supposez qu'un groupe de soldats doit en garder l'entrée. Vous pouvez vous préparer à passer en force, essayer de trouver une autre entrée ou tenter de vous camoufler.")
        ),mapOf(
            Pair("ETAPE15 : Bataille",15),
            Pair("ETAPE16 : Retraite",16),
            Pair("ETAPE17 : Camouflage",17)
        )
        )

        val e15 = Stage(15,listOf(
            StageElement(StageElement.Companion.TYPE.LCK,"18"),
            StageElement(StageElement.Companion.TYPE.TXT, "3 créatures infernales vous tombent dessus") ,
            StageElement(StageElement.Companion.TYPE.IMG, "e15i0"),
            StageElement(StageElement.Companion.TYPE.TXT, "Pendant que certain retiennent difficilement ces soldats du démon, le vieux vous tend un parchemin magique. Lancer le sort qu'il contient est votre seule chance de vous en sortir") ,
            StageElement(StageElement.Companion.TYPE.IMG, "e15i1"),
            StageElement(StageElement.Companion.TYPE.EDT,"Lancer un sort",additional = arrayOf("LOSE MOMENTUM"))
        ),mapOf(
            Pair("ETAPE18 : Donjon",18)
        ),listOf(
            listOf(
                StageElement(StageElement.Companion.TYPE.TXT, "Les soldats infernaux sont immobilisés !") ,
                StageElement(StageElement.Companion.TYPE.UCK,"18"),
                StageElement(StageElement.Companion.TYPE.TXT, "Vous vous apercevez que le vieux a pris un coup mortel durant le combat. Il repose au sol, l'air tranquille. Le pauvre n'aura pas profité longtemps de la liberté. Vous entrez dans le donjon et commencez à monter.") ,
                StageElement(StageElement.Companion.TYPE.VAR, "vieux=0")
            )
        )
        )

        val e16 = Stage(15,listOf(
            StageElement(StageElement.Companion.TYPE.TXT, "Bien joué ! Une porte dérobée vous permet d'entrer. Il n'y a aucun garde ici. Prudemment vous entrez dans le donjon et en entamez l'ascension.") ,
            StageElement(StageElement.Companion.TYPE.IMG,"e14i0")

        ),mapOf(
            Pair("ETAPE18 : Donjon",15)
        )
        )

        val e17 = Stage(17,listOf(
            StageElement(StageElement.Companion.TYPE.LCK,"18",condition = "skill != 1"),
            StageElement(StageElement.Companion.TYPE.TXT, "Votre camouflage ne tient pas la route et les monstres vous repèrent instantanément. Vous allez devoir vous battre !",condition = "skill != 1") ,
            StageElement(StageElement.Companion.TYPE.BTN,"Engager le combat",condition = "skill != 1"),
            StageElement(StageElement.Companion.TYPE.TXT, "Votre camouflage est parfait et vous passez devant 3 créatures infernales sans vous faire voir.",condition = "skill == 1"),
            StageElement(StageElement.Companion.TYPE.IMG,"e15i1",condition = "skill == 1"),
            StageElement(StageElement.Companion.TYPE.TXT, "Le vieux tremble, mais vous le tenez fermement pour qu'il ne fasse pas de bêtise. Une fois le son des soldats estompé, vous le lâchez enfin, et entamez la montée du donjon.",condition = "skill == 1"),

            ),mapOf(
            Pair("ETAPE18 : Donjon",18)
        ),listOf(
            listOf(
                StageElement(StageElement.Companion.TYPE.ETP,"15")
            )
        )
        )

        val e18 = Stage(18,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous montez en courant depuis de longues minutes, lorsque vous arrivez à un palier d'oû part un couloir enfumé, rempli de statuettes. Vous avancez dans le couloir. A son extrémité, une issue est présente de chaque coté"),
            StageElement(StageElement.Companion.TYPE.IMG,"e18i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Le vieil homme vous fait signe que les deux chemins montent au sommet. Vous pouvez donc choir celui que vous voulez.",condition = "vieux == 1"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous entendez des coups sourd venant de la gauche, comme des pas lourds et désordonnés"),
            StageElement(StageElement.Companion.TYPE.TXT,"Votre 6e sens s'active soudain, comme un appel très fort. Vous savez que le bruit de pas du couloir gauche vous est familier. Mieux, il vous donne confiance.",condition = "skill == 2 && renfort == 1"),
            StageElement(StageElement.Companion.TYPE.TXT,"Votre 6e sens s'active soudain, comme un appel très fort. Le bruit de pas du couloir gauche vous est extrêmement désagréable. Du genre qu'il faut éviter à tout prix.",condition = "skill == 2 && renfort != 1"),
            StageElement(StageElement.Companion.TYPE.TXT,"Gauche ou droite ? Vous vous décidez et vous engagez dans l'issue choisie")

        ),mapOf(
            Pair("ETAPE19 : DonjonG",19),
            Pair("ETAPE20 : DonjonD",20)
        )
        )

        val e19 = Stage(19,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Vous montez les escaliers à gauche. Les pas sont de plus en plus fort. Enfin vous les apercevez. Il s'agit de grands guerriers revêtus d'armures et lourdement armés."),
            StageElement(StageElement.Companion.TYPE.IMG,"e19i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"L'un d'eux s'avance et vous gratifie d'un salut. Vous reconnaissez leur armure, qui est celle des soldats d'élite du comté à l'est de Kalte. Vous lui rendez son salut. Il a reconnu votre blason également, et vous indique qu'ils sont venu à cause d'un signal de la forme d'un feu qui a été allumé sur une des tours. Ils se joignent à votre troupe et vous reprenez l'ascension du donjon.",condition = "renfort == 1"),
            StageElement(StageElement.Companion.TYPE.TXT,"En reprenant la marche, vous remarquez que le vieil homme a profité de la bousculade pour s'éclipser. Tant pis, vous allez devoir continuer sans lui",condition = "renfort == 1"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous reconnaissez leurs armures comme celle de la milice du comté à l'est de Kalte, mais leur visage n'exprime plus rien. De toute évidence, ils sont envoûtés par le sorcier. Vous allez devoir vous défendre",condition = "renfort == 0"),
            StageElement(StageElement.Companion.TYPE.VAR,"renfort = 2",condition = "renfort == 1")
        ),mapOf(
            Pair("ETAPE21 : Porte",21)
        ))

        val e20 = Stage(20,listOf(
            StageElement(StageElement.Companion.TYPE.LCK,"21"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous vous engagez dans l'escalier de droite. Mais tout en haut, une porte fermée bloque la voie. Il n'y a pas de serrure apparente. Il faut probablement lui dire quelque chose. Un étrange mur se trouve tout à coté :"),
            StageElement(StageElement.Companion.TYPE.IMG,"e20i0"),
            StageElement(StageElement.Companion.TYPE.EDT,"Dire", arrayOf("vampire"))
        ),mapOf(
            Pair("ETAPE21 : Porte",21)
        ),listOf(
            listOf(
                StageElement(StageElement.Companion.TYPE.TXT,"Bien joué ! La porte s'ouvre lentement. Vous vous avancez ..."),
                StageElement(StageElement.Companion.TYPE.UCK,"21")
            )
        )
        )

        val e21 = Stage(21,listOf(
            StageElement(StageElement.Companion.TYPE.LCK,"22"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous êtes enfin au sommet du donjon. Un grand tapis de velour rouge recouvre la pièce. Deux grandes fenêtres laissent apercevoir l'horizon derrière la vallée enneigée."),
            StageElement(StageElement.Companion.TYPE.TXT,"  Le vieil homme s'avance et vous dit 'Comme promis, je vais vous montrer ou se trouve le trésor'.\n Il s'approche d'un mur, et appuie dessus. Le mur coulisse. ",condition = "vieux == 1"),
            StageElement(StageElement.Companion.TYPE.IMG,"e21i1",condition = "vieux == 1"),
            StageElement(StageElement.Companion.TYPE.TXT,"Puis il pointe l'opposé de la grande salle : 'Le sorcier se trouve là !'",condition = "vieux == 1"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous traversez prudemment la salle rouge. Une porte immense vous fait face."),
            StageElement(StageElement.Companion.TYPE.IMG,"e21i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Cinq vers sont gravés sur le fronton : \n\nSans voix , il hurle,\nSans ailes , il voltige,\nSans dents , il mord\nSans bouche , il murmure,\nSans main, il caresse."),
            StageElement(StageElement.Companion.TYPE.EDT,"Prononcer",additional = arrayOf("vent","le vent"))
        ),mapOf(
            Pair("ETAPE22 : Boss",22)
        ),listOf(
            listOf(
                StageElement(StageElement.Companion.TYPE.TXT,"Un claquement se produit, les battants se séparent et la porte commence à s'ouvrir. Vous rassemblez votre courage et avancez vers un dernier combat."),
                StageElement(StageElement.Companion.TYPE.UCK,"22")
            )
        )
        )


        val e22 = Stage(22,listOf(
            StageElement(StageElement.Companion.TYPE.LCK,"23"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous vous avancez.Le sorcier est la, il incante devant un portail gigantesque. Vous pouvez sentir une aura maléfique qui s'en dégage."),
            StageElement(StageElement.Companion.TYPE.IMG,"e22i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Le sorcier se tourne vers vous. Il rit doucement en vous voyant : 'Vous avez du avoir du mal à arriver jusqu'ici, je reconnais votre bravoure. Mais c'est en vain ! Vous allez simplement périr les premiers'"),
            StageElement(StageElement.Companion.TYPE.TXT,"Vous allez devoir livrer bataille pour vaincre le sorcier et fermer la porte"),
            StageElement(StageElement.Companion.TYPE.TXT,"Heureusement les renforts que vous avez reçu vous donne un avantage indéniable",condition = "renfort == 2")
        ),mapOf(
            Pair("ETAPE23 : Fin",23)
        )
        )

        val e23 = Stage(23,listOf(
            StageElement(StageElement.Companion.TYPE.TXT,"Victoire ! Le sorcier est vaincu. La menace est écarté. Le portail s'emballe et va exploser. Vous vous enfuyez du chateau et reprenez la route vers le Sud. Un vent de salvation souffle derrière vous."),
            StageElement(StageElement.Companion.TYPE.IMG,"e23i0"),
            StageElement(StageElement.Companion.TYPE.TXT,"Malgré la précipitation, vous avez réussi à emporter avec vous une grande partie du trésor. Vous êtes victorieux sur tout les tableaux. Une grande vie vous attend.", condition = "vieux == 1"),
            StageElement(StageElement.Companion.TYPE.IMG,"e21i1",condition = "vieux == 1"),
            StageElement(StageElement.Companion.TYPE.TXT,"Longtemps on parlera de cette exploit que vous avez accompli. On se racontera cette aventure au coin du feu, on la jouera sur scene, on l'écrira et on la chantera.\nEt bientôt, très bientôt, vous serez loués pour bien d'autres aventures"),
            StageElement(StageElement.Companion.TYPE.IMG,"e23i1"),
        ),mapOf())


        val etapes = listOf(e0,e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14,e15,e16,e17,e18,e19,e20,e21,e22,e23)

        val desc = "Venez bravez la froide contrée de Kalte pour sauver le monde d'un terrible sorcier"
        val cpr = "Scenario tiré du livre : 'Les grottes de Kalte' de la série 'Loup Solitaire' de Joe Dever\n Images du jeu 'The Elder Scrolls Online' de Bethesda SoftWorks"

        val s = Scenario("Kalte","fx",desc,cpr,stages = etapes)
        s.variables["skill"] = 0
        s.variables["loup"] = 3
        s.variables["chemin"] = 0
        s.variables["tour"] = 0
        s.variables["renfort"] = 0
        s.variables["cochon"] = 0
        s.variables["vieux"] = 0
    }
*/
}
