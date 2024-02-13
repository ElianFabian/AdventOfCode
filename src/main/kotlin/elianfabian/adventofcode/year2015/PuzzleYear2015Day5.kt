package elianfabian.adventofcode.year2015

import elianfabian.adventofcode.util.AocPuzzle

/**
 * --- Day 5: Doesn't He Have Intern-Elves For This? --- https://adventofcode.com/2015/day/5
 */
object PuzzleYear2015Day5 : AocPuzzle(2015, 5) {

	override val partOneQuestion = "How many strings are nice?"

	/**
	 * Santa needs help figuring out which strings in his text file are naughty or nice.
	 *
	 * A nice string is one with all of the following properties:
	 *
	 * It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
	 * It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
	 * It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
	 * For example:
	 *
	 * - ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
	 * - aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
	 * - jchzalrnumimnmhp is naughty because it has no double letter.
	 * - haegwjzuvuyypxyu is naughty because it contains the string xy.
	 * - dvszwmarrgswjxmb is naughty because it contains only one vowel.
	 *
	 * How many strings are nice?
	 */
	override fun getResultOfPartOne(): Int {
		val allStrings = input.lineSequence()

		return allStrings.count { isStringNiceInPartOne(it) }
	}

	override val partTwoQuestion = "How many strings are nice under these new rules?"

	/**
	 * Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or nice. None of the old rules apply, as they are all clearly ridiculous.
	 *
	 * Now, a nice string is one with all of the following properties:
	 *
	 * It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
	 * It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
	 * For example:
	 *
	 * - qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
	 * - xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
	 * - uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter between them.
	 * - ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.
	 *
	 * How many strings are nice under these new rules?
	 */
	override fun getResultOfPartTwo(): Int {
		val allStrings = input.lineSequence()

		return allStrings.count { isStringNiceInPartTwo(it) }
	}

	override val input = getInput()
}


//region Utils

private fun isStringNiceInPartOne(string: String): Boolean {
	return hasAtLeastThreeVowels(string) && hasAtLeastOneLetterThatAppearsTwiceInARow(string) && notContainsThePairsAbCdPqXy(string)
}

private fun isStringNiceInPartTwo(string: String): Boolean {
	return containsAPairOfTwoLettersThatAppearsAtLeastTwiceWithoutOverlapping(string) && containsAtLeastOnLetterWhichRepeatsWithOneLetterBetweenThem(string)
}

private fun hasAtLeastThreeVowels(string: String): Boolean {
	val minVowelCount = 3
	val vowels = "aeiou"

	val vowelsCount = string.count { it in vowels }

	return vowelsCount >= minVowelCount
}

private fun hasAtLeastOneLetterThatAppearsTwiceInARow(string: String): Boolean {

	string.forEachIndexed { index, currentChar ->

		val nextChar = string.getOrNull(index + 1)

		if (currentChar == nextChar) {
			return true
		}
	}

	return false
}

private fun notContainsThePairsAbCdPqXy(string: String): Boolean {
	val forbiddenPairs = arrayOf("ab", "cd", "pq", "xy")

	string.forEachIndexed { index, currentChar ->

		val nextChar = string.getOrNull(index + 1)

		val currentPair = "$currentChar$nextChar"

		if (currentPair in forbiddenPairs) {
			return false
		}
	}

	return true
}

private fun containsAPairOfTwoLettersThatAppearsAtLeastTwiceWithoutOverlapping(string: String): Boolean {
	val minPairCount = 2
	val letterPairsAndCount = mutableMapOf<String, Int>()

	string.forEachIndexed { index, currentChar ->

		val previousChar = string.getOrNull(index - 1)
		val nextChar = string.getOrNull(index + 1)

		val currentPair = "$currentChar$nextChar"

		if (currentPair in letterPairsAndCount) {
			val currentCount = letterPairsAndCount[currentPair]!!
			val newCount = currentCount + if (currentChar == previousChar && currentChar == nextChar) -1 else 1

			letterPairsAndCount[currentPair] = newCount

			if (newCount >= minPairCount) {
				return true
			}
		}
		else {
			letterPairsAndCount[currentPair] = 1
		}
	}

	return false
}

private fun containsAtLeastOnLetterWhichRepeatsWithOneLetterBetweenThem(string: String): Boolean {
	string.forEachIndexed { index, currentChar ->

		val previousChar = string.getOrNull(index - 1)
		val nextChar = string.getOrNull(index + 1)

		if ((previousChar == nextChar) && (currentChar != previousChar)) {
			return true
		}
	}

	return false
}

//endregion


private fun getInput() = """
        rthkunfaakmwmush
        qxlnvjguikqcyfzt
        sleaoasjspnjctqt
        lactpmehuhmzwfjl
        bvggvrdgjcspkkyj
        nwaceixfiasuzyoz
        hsapdhrxlqoiumqw
        lsitcmhlehasgejo
        hksifrqlsiqkzyex
        dfwuxtexmnvjyxqc
        iawwfwylyrcbxwak
        mamtkmvvaeeifnve
        qiqtuihvsaeebjkd
        skerkykytazvbupg
        kgnxaylpgbdzedoo
        plzkdktirhmumcuf
        pexcckdvsrahvbop
        jpocepxixeqjpigq
        vnsvxizubavwrhtc
        lqveclebkwnajppk
        ikbzllevuwxscogb
        xvfmkozbxzfuezjt
        ukeazxczeejwoxli
        tvtnlwcmhuezwney
        hoamfvwwcarfuqro
        wkvnmvqllphnsbnf
        kiggbamoppmfhmlf
        ughbudqakuskbiik
        avccmveveqwhnjdx
        llhqxueawluwmygt
        mgkgxnkunzbvakiz
        fwjbwmfxhkzmwtsq
        kzmtudrtznhutukg
        gtvnosbfetqiftmf
        aoifrnnzufvhcwuy
        cldmefgeuwlbxpof
        xdqfinwotmffynqz
        pajfvqhtlbhmyxai
        jkacnevnrxpgxqal
        esxqayxzvortsqgz
        glfoarwvkzgybqlz
        xdjcnevwhdfsnmma
        jyjktscromovdchb
        pvguwmhdvfxvapmz
        iheglsjvxmkzgdbu
        lwjioxdbyhqnwekv
        zcoguugygkwizryj
        ogvnripxxfeqpxdh
        hkvajhsbfnzsygbm
        cnjqeykecopwabpq
        wojjtbcjinoiuhsj
        kpwpvgxbyzczdzjq
        wrvhylisemlewgzk
        uiezkmnhilfzahtm
        mucteynnuxpxzmvt
        zaiwbgxefusfhmst
        apptbogpxivjwink
        qryboarjtwjhjgjb
        irehxupgyseaahzd
        fobstqxguyubggoh
        ysriumfghtxtfxwe
        auchdmasvfeliptw
        mztuhefcrnknyrdl
        tyjmkhihbwabjtaa
        yquzkdtgsljkaebw
        almvdvofjtkyzbmd
        emqftiuqqpdwwbrv
        hrrhmqfpepvbawvw
        atrkgykycvgxbpyb
        dhthetnealksbdan
        zzqafhgicubptiyo
        qdtaieaziwhbttnw
        kyskgapdgqrtrefw
        edwzlpqztpydmdlr
        awszjnlmvlyqsuvl
        kcrtmtshtsgixvcp
        jtaskgkijivbbkri
        mmggfwapsetemiuj
        itagrrnjbnmhgppd
        uqmbezechbrpbnqq
        nnyimvtascflpzsa
        knqeimypkdttyudj
        vgoiyvtvegwyxjjd
        qubzdxsbecktzrho
        zehojtvktsbbxijb
        xepmjrekwcgoxyoh
        bnptxnocbpbqbyeq
        sfvynsywscbnymos
        dsltfbpcmffbluba
        kncrlzlmkikylppa
        siwudrvmildgaozv
        jhhefbvbvneqzvtc
        lqjgztxitbuccqbp
        himmwlbhjqednltt
        vwognchyertnnfil
        eejakhapkbodrntf
        qxuijkkhhlskgrba
        aankpfxxicfpllog
        vuxykvljyqexfhrn
        epgygflbxlbwybzq
        zuxmwvetmvcszayc
        xttwhfqmemgtjnkf
        hftwldmivyfunfvl
        bejlyxfamzliilrj
        zkehazcxyyvtrxti
        dsgafehmcfpycvgz
        igremmqdojqdvwmb
        swnjzvmhcslvkmiw
        fchzbfbmtqtxmaef
        xwjmyyrlznxrcytq
        brwcwzpcvbwdrthl
        fvrlridacsiojdmb
        mhsturxdlmtxozvy
        usxvqyrwywdyvjvz
        gwazuslvmarfpnzm
        rgkbudaqsnolbcqo
        dpxvlbtavdhdedkj
        nnqmjzejhodyfgyd
        ozoazxkfhujgtzvy
        psdgvhzdiwnuaxpl
        tznkilxpogbzgijz
        wnpytcseirtborhh
        lhauurlfsmagfges
        oqfbzixnlywkzwwy
        yoehapoyjpakziom
        vtjftdcsfdzbmtrn
        zcshfnodiwixcwqj
        wapbxpaxgjvtntkm
        qfyypkyvblrtaenh
        bsxhbxkovgukhcza
        kitdmvpiwzdonoyy
        slkbhxmehzavbdsf
        dovzjouqkzkcmbkl
        qpbigdcqkfnfkxvq
        eaiaquhnesvtcdsv
        mhbezlhqojdsuryj
        dqprkkzxlghkoccx
        xqepmorryeivhrhm
        frwmrjpezwmjflvf
        gjpfgwghodfslwlf
        fzyvajisdjbhfthq
        pvzxkxdscdbilrdb
        mtaxmqcnagmplvnm
        rlyafujuuydrqwnc
        gvqvrcxwyohufehq
        lmrkircgfrfusmfd
        ovlpnkxcpimyaspb
        xhyjremmqhdqywju
        pxfczlhpzbypfarm
        utjhprzhtggausyp
        utzkkzlnyskjtlqh
        cecbcnxpazvkedic
        xwvoaggihrbhmijq
        krredhmtwlfmyagw
        lwfhxgbknhwudkzw
        vyczyvuxzmhxmdmn
        swcoaosyieqekwxx
        waohmlfdftjphpqw
        gaclbbfqtiqasijg
        ybcyaxhluxmiiagp
        xgtxadsytgaznndw
        wzqhtjqpaihyxksm
        fdwltsowtcsmsyhm
        rpoelfbsararhfja
        tswgdacgnlhzwcvz
        xjgbhdlxllgeigor
        ksgthvrewhesuvke
        whgooqirdjwsfhgi
        toztqrxzavxmjewp
        hbkayxxahipxnrtl
        lazimkmdnhrtflcu
        ndoudnupbotwqgmr
        niwuwyhnudxmnnlk
        hlmihzlrpnrtwekr
        wzkttdudlgbvhqnc
        rfyzzgytifkqlxjx
        skddrtwxcyvhmjtb
        mljspkvjxbuyhari
        xwkhozaoancnwaud
        nookruxkdffeymdz
        oiqfvpxmcplyfgoa
        qoxggshmrjlzarex
        lsroezewzkrwdchx
        nkoonmvdydgzspcl
        lygxeqztdqklabov
        jempjyzupwboieye
        hpdaqkhjiddzybly
        cvcizjlnzdjfjlbh
        vaaddsbkcgdjhbkj
        pjxmtxoyrkmpnenf
        ujqdvyqnkbusxlps
        miyvzkzqploqaceb
        gapcsbkulicvlnmo
        xqpcyriqhjhaeqlj
        ipumdjwlldzqhmgh
        swdstecnzttmehxe
        ucmqordmzgioclle
        aywgqhmqlrzcxmqx
        ptkgyitqanvjocjn
        wcesxtmzbzqedgfl
        rnetcouciqdesloe
        chpnkwfdjikqxwms
        onpyrjowcuzdtzfg
        tydnqwaqwkskcycz
        dhamguhmkjzzeduy
        oecllwyrlvsyeeuf
        gsukajpoewxhqzft
        sgdnffdixtxidkih
        pqqzjxzydcvwwkmw
        wnjltltufkgnrtgm
        hylaicyfrqwolnaq
        ovfnugjjwyfjunkm
        xknyzsebmqodvhcl
        uwfmrjzjvvzoaraw
        zaldjvlcnqbessds
        zphvjuctrsksouvz
        ceqbneqjwyshgyge
        wmelhaoylbyxcson
        nghuescieaujhgkj
        dhjmflwwnskrdpph
        exvanqpoofjgiubf
        aidkmnongrzjhsvn
        mdbtkyjzpthewycc
        izctbwnzorqwcqwz
        hrvludvulaopcbrv
        mrsjyjmjmbxyqbnz
        sjdqrffsybmijezd
        geozfiuqmentvlci
        duzieldieeomrmcg
        ehkbsecgugsulotm
        cymnfvxkxeatztuq
        bacrjsgrnbtmtmdl
        kbarcowlijtzvhfb
        uwietqeuupewbjav
        ypenynjeuhpshdxw
        fwwqvpgzquczqgso
        wjegagwkzhmxqmdi
        vocvrudgxdljwhcz
        nnytqwspstuwiqep
        axapfrlcanzgkpjs
        lklrjiszochmmepj
        gxadfpwiovjzsnpi
        qidsjxzgwoqdrfie
        wgszciclvsdxxoej
        kwewlmzxruoojlaq
        ywhahockhioribnz
        ucbqdveieawzucef
        mdyyzmfoaxmzddfv
        hsxnabxyqfzceijv
        vivruyvbrtaqeebr
        jxfeweptjtgvmcjc
        mmypqxmpurhculwd
        mpiaphksvctnryli
        xqzqnuxmuzylkkun
        fndmtefjxxcygtji
        dnorqlldvzqprird
        nutokyajmjpwjaqu
        vlupfperqyqkjcaj
        dgihjeokrphkpdnk
        nvbdyrlheqzixuku
        mhrkntnxvsmvrpka
        kvhkyanlhhymwljf
        fhipumtegqfgeqqw
        vpfjgveycdefuabu
        kzincljffncylcsf
        tsezxymwmjtyegqw
        wxhcdrqedkdcwxli
        ueihvxviirnooomi
        kfelyctfvwyovlyh
        horzapuapgtvzizz
        iiqkdpmfvhwwzmtj
        rsaclclupiicstff
        quwkkhrafypkaoum
        gyrgkgmwqfkeudfe
        noydhbqacwptyfmy
        efwwuipzgtkwffhf
        suyojcitomdxsduh
        lbcxnsykojkufkml
        zpglsvoutvzkgdep
        usgrufyvgsbsmbpr
        katrrwuhwvunjqor
        btngwrpcxoyfbgbc
        bxjscjdiowjrkpns
        nwxvnfrnlkgqxvhf
        ikhyqkvljucgdlag
        xibnxsjopmxvflkl
        mzplumcfivqcjqnz
        jqflcxoxzlbwlxry
        fcscvmfepdxrshxe
        wlpffwunffklzbuc
        emvrlqajjgwzfmle
        rhaheurtzrfoqkyq
        ifuuhpxmadaysfsx
        ncyfvleyzqntpcoo
        zeogmyaqccmtvokd
        jqppbzebppdnpurn
        xixarswxsiwjzgni
        ezruwzajsoombphs
        hmiqfeizyprielxf
        jnaoxljnftymsfey
        extgzrxzovlsixnf
        yhyfmovvlrwoezsv
        ffnybaolppuzpjym
        pqowimdiusccaagn
        jgceiosiihpjsmnu
        hkoexeaopebktngx
        njhzuvsygymejqav
        yjkgcclgtvushcfk
        gmbjxhnkkxlihups
        pdlwysadiebsidjz
        omrwmgzulfoaqros
        ofvvgdezwvcffdcy
        otytpuklhxcpxhgd
        eyfaosxdauumvlux
        mvdthjfstrlqlyuo
        mdgdchgnlxaxspdm
        bakjezmhbwqxzevd
        msakswaphdwaodhg
        vjcqscgdbnsxdllh
        jjywaovewbuzreoj
        nqvplhwacylifvwk
        lpwmpixbxysmsign
        flcvbpxrchcpbgcb
        qjpkeuenenwawlok
        bnqkflfmdmntctya
        fzsgzpoqixvpsneq
        icwfdisutoilejld
        relchofohnkwbumi
        aljalgdaqwhzhfwr
        cahkvnwnbwhodpqs
        dnrzeunxiattlvdm
        nsmkhlrpwlunppjs
        mqqsexlwfqnogwub
        tfavelkqrtndpait
        ooguafrnmprfxcnz
        ntynkiordzxtwrqa
        rkkyzlxekqqlkvym
        ofxcivdnwcmgfnme
        ywotqwbrqxlrnobh
        nrbbiypwhrqihvev
        flqsjixxtydheufs
        lcfrfzypstrqctja
        hyzbuzawuzjrynny
        exfbywcnstebnvmq
        vydzwnbmcihvqrnj
        qmwqaaylinzrdmiw
        lpxpztpvfggspeun
        lhxmqqbracsuyrfm
        zgkwsrabaseidbrw
        yjlmbhbqsqgszsun
        mqfzqtbxtuteabtd
        izomzdmcqmfrevwd
        iqijrlqurdwrkoln
        fxhqzpgoxxjkkhql
        oulwontmgrjeopnk
        edaigfydjexvzzvj
        vjhybiklxpxjqpwc
        ypxfbfnpbmqmwtte
        xzvcsgasztrxdzud
        rpulqmobptfarboo
        palacmdijxzzykrf
        jmllwukplufohiby
        dnswayomusiekfmy
        sxbrjqtqgzzwhcfo
        lylvndsgbnbqiejm
        jaxxhoulxnxnaenr
        nblissutfazbcpwn
        zmlsjszzldvbiacr
        kewojtlchfkclqwk
        eqvfjasddggvfame
        yibzqlvxtraxpdon
        dgnbxsbmdrtyvaac
        uoxrcxfimhgtxqhy
        xfdxalrwcwudlviq
        xmtbdklqptoswpwl
        zezyopzdztdjerfl
        xuzluhjsqvhytgbc
        qdjtmeckispmgzki
        phakupesplzmmmvc
        gpuoqfffumzszybn
        bhywxqkrrlwuebbw
        ibvwgoyvelzenkzl
        ncohvvbmiekbaksa
        fzuvqzvxvdbeirrp
        lshtzniokucwojjd
        punrduvlnrulkium
        gnfpikidnfobrrme
        vxkvweekmnvkzgyl
        rhydssudkcjlqgxn
        cjtqvlaahohcgumo
        jwzmfyinsfwecgcb
        blpeseqhlzfilpuf
        jvtpjkyokzcvagon
        qjomincbcobjczpe
        ugsyzkzgdhxtmsfz
        hleaqgwzqjwajcra
        coumfghptpnxvvov
        hqpnbupnzwpdvgqd
        cpouyodqxgviasem
        lljvxeyozckifhfd
        huqtnvutdyfgwtwa
        yenlveuynmlmmymu
        ojdyufkomxiwjmbf
        spjzgvcwvzgffjkk
        vxykmjhyvmhyssbp
        tazdeqggfcjfvwwn
        uumwcngwcytvpufx
        avovuzkrevloneop
        owczrtbnrvjfemkt
        hzpugcanaxyvaokj
        iishlodnxvjtgzyn
        qosdonclrnxirham
        eonqlnwevahydddg
        ryqmnuikftlxuoqy
        whqepbcwabzbthha
        vekisvnwhgpyemxr
        lrwxzoamnvpnlhap
        ywepvqthnorfswjv
        evqwvsoazmwyypjy
        bgwoojddubppmjxf
        jypkfrthzgtyeddi
        tynabbhfjzkrqsju
        adxstbfqheuqbcuk
        gqwqiocdyqoiblrx
        ybuddlyuskdlegxv
        luwynbsmpgyeqsbr
        ltyqgqoyljibqndo
        jaedpajzphfybajh
        epglnrxofptsqvmy
        zjdpxkngfkstxbxh
        ekegphcwanoickfu
        cqvhuucvejqirvfs
        uqudnnqumsqcgefo
        qnzunermlnpcfflo
        ovyxaniqaawzfuxx
        djekxcezjowdhopq
        bwtwbmdehrhpjnlk
        nilsnlacerweikfa
        hyrigsrmsrzcyaus
        gvmdmgddduylmxic
        ewzovdblhmjgjwsk
        ojjfsknlonzguzlq
        yjgfruvpjvlvrvvq
        cyoryodwyhzwprbv
        crsjclrurcquqgut
        sjhfhobwtojxcmem
        ibxfjudilmdeksea
        uqbhdbjoeupyhbcz
        uqbxigzxuxgmjgnw
        jashafmtzrhswirg
        dexiolovaucyooka
        czjbwwnlwcoqnoiu
        ojigosazigfhttjc
        zfiqtgrqbmftknzn
        dlzbmvmolssbqlzl
        sgmchcurrutdtsmw
        scdwjqsdohcdrwry
        cgtdvecqwplpprxn
        iiplenflfczaktwi
        wmgnwfxfcjhyeiqg
        giihshowtcatecvl
        nqhzfincclumvkaz
        kxstpzgdfvepionc
        agbhxcijxjxerxyi
        hmgfqevgdyvisyvs
        tthakmvpowpvhtao
        ottalcghygpaafbo
        aplvozayycremgqg
        dbjxlnaouxqtdpfz
        peeyallzjsdvpalc
        ndtdjyboixuyhfox
        llabnbcobexfoldn
        cweuvfnfyumbjvxr
        ewkhhepaosalnvkk
        pivyiwsiqpwhagyx
        auzsnwdcerfttawt
        grbfrekupciuzkrt
        byfwzadtzrbndluf
        lluypxjeljzquptk
        pskwsnhqanemtfou
        sxvrtqqjdjkfhhrm
        ulsmqgmshvijyeqh
        qigofesfhekoftkf
        zhatniakqtqcxyqa
        uuczvylgnxkenqee
        mlitvtuxknihmisc
        srrtrxdvcokpyfmz
        osispuucklxcfkeb
        vqhazlaulmnpipql
        umkiueljberqhdig
        knvpbkbvgoqzwprp
        nbsocqikhuvsbloj
        wjnpepjkzkednqbm
        agbhmytsofuyqcor
        gvogzhkkpxyfecko
        ardafguxifeipxcn
        yiajcskbgykyzzkw
        sejunbydztyibnpq
        dqrgfggwcnxeiygy
        xnqqwilzfbhcweel
        jjtifhlvmyfxajqi
        gwszrpgpmbpiwhek
        kydzftzgcidiohfd
        efprvslgkhboujic
        kecjdfwqimkzuynx
        rildnxnexlvrvxts
        dlnhjbqjrzpfgjlk
        qluoxmzyhkbyvhub
        crydevvrjfmsypbi
        dosaftwumofnjvix
        pwsqxrfwigeffvef
        nzyfmnpwqyygjvfx
        iccbckrkxlwjsjat
        bmputypderxzrwab
        bhuakynbwnlreixb
        qmrzfyqjiwaawvvk
        juvtixbkwyludftn
        zapmjxmuvhuqlfol
        paiwrqjhpjavuivm
        tsepfbiqhhkbyriz
        jpprewufiogxoygk
        mmapyxbsugcsngef
        pduhmgnepnpsshnh
        aetndoqjvqyjrwut
        fnfvlorhwpkkemhz
        gedfidpwvoeazztl
        beclvhospgtowaue
        wsclsvthxustmczm
        tjbxhnpniuikijhe
        rhetyhvfcemponeg
        mavonujurprbeexi
        argbrpomztrdyasa
        bzvtffbtygjxmkvh
        maqyqkhsqgzfzvve
        seeirbiynilkhfcr
        wxmanwnozfrlxhwr
        dieulypsobhuvswb
        nxevassztkpnvxtb
        jclxuynjsrezvlcy
        xlolzyvgmwjsbmyf
        tguzoeybelluxwxc
        fkchoysvdoaasykz
        cyynwbfcqpqapldf
        rhifmzpddjykktuy
        ndvufsyusbxcsotm
        txutnzvdsorrixgg
        qjoczhukbliojneu
        ufhwujotncovjjsz
        kclsgsdwcrxsycbr
        yscwmlrdaueniiic
        nxhivrovpkgsmugb
        fdxqfyvwwvgeuqkv
        femtamfylysohmpr
        amsyzslvyxsoribh
        nhmqxncwsonhgbcz
        uomqsvcbpthlmcue
        kxtfapcqrnjkkslj
        xtieihonlfubeync
        adpcjqxgydulchgj
        cjynnzsmmujsxxpd
        neeapmzweidordog
        szoivgqyqwnyjsnk
        uwgrtzaqezgphdcu
        ptpgttqxocjwxohi
        fhltebsizfwzpgpf
        emmsazsidspkhgnh
        dxcprkbcjeqxqzgn
        tpxzqwxbzwigdtlt
        afsmksnmzustfqyt
        xyehnftstacyfpit
        vcrfqumhjcmnurlw
        rrznpjzcjgnugoch
        gbxnzkwsjmepvgzk
        jwobshgwerborffm
        zmuvfkhohoznmifs
        buyuwgynbtujtura
        bevncenmpxfyzwtf
        hqqtcrhzfsrcutjh
        kbpzshllpiowepgc
        alspewedcukgtvso
        xvsvzzdcgjuvutrw
        pmwulqraatlbuski
        abuzsiinbueowpqn
        oedruzahyfuchijk
        avhcuhqqjuqkesoq
        azqgplkzsawkvnhb
        rjyoydogkzohhcvx
        aezxwucqvqxuqotb
        kxobnsjvzvenyhbu
        nnjoiilshoavzwly
        aijttlxjrqwaewgk
        cvsaujkqfoixarsw
        zngtoacpxcsplgal
        qhkxliqtokvepcdv
        aixihrtdmxkfvcqw
        owbgdgdymxhhnoum
        tajsagmruwzuakkd
        ckrfduwmsodeuebj
        alfdhuijuwyufnne
        xpchlkijwuftgmnm
        rwcrvgphistiihlg
        xdaksnorrnkihreq
        akeschycpnyyuiug
        rgputhzsvngfuovz
        lerknhznuxzdhvre
        mqiqmyladulbkzve
        csnmupielbbpyops
        kwgrwgmhfzjbwxxz
        npwtvbslvlxvtjsd
        zxleuskblzjfmxgf
        hexvporkmherrtrn
        rhtdhcagicfndmbm
        qhnzyuswqwoobuzz
        dpvanjuofrbueoza
        kjcqujmnhkjdmrrf
        gholddsspmxtpybg
        jihlvyqdyzkshfsi
        zuviqmuqqfmtneur
        kzexjowatvkohrtx
        wgijnfhibsiruvnl
        zevkrkmhsxmicijb
        khxrcteqourjvoxa
        ylpxlkcnenbxxtta
        zrfsvctbojjkpvtw
        nlzbudxibnmcrxbt
        cqnscphbicqmyrex
        ywvdohheukipshcw
        riwatbvjqstubssf
        idlztqqaxzjiyllu
        sdpdgzemlqtizgxn
        rjtbovqlgcgojyjx
        fnfrfwujmjwdrbdr
        osnppzzmrpxmdhtj
        ljhwngclvydkwyoe
        chwqkrkzrvjwarat
        jmydkwpibkvmqlgs
        zvhfmbxnlxtujpcz
        jsnhsphowlqupqwj
        fzhkkbpasthopdev
        jerntjdsspdstyhf
        gctwmaywbyrzwdxz
        xemeaiuzlctijykr
        xulrqevtbhplmgxc
        yfejfizzsycecqpu
        gboxrvvxyzcowtzm
        lpvhcxtchwvpgaxp
        wdiwucbdyxwnjdqf
        qgwoqazzjlvnjrwj
        prtlnkakjfqcjngn
        fagvxsvjpuvqxniz
        xacmxveueaakfbsm
        ginvtonnfbnugkpz
        qpvggsppewfzvwin
        reoqnlzruyyfraxa
        kolwtqhifjbbuzor
        vrkcywvdhdprztww
        ngdvyfmvjqhbzbxt
        rooxeoilqzqjunmp
        efxmdprtogtxgyqs
        qrhjuqndgurcmwgu
        ouitjprueefafzpl
        kirdwcksqrbwbchp
        fpumsmogojuywezo
        lgjrgykywugzjees
        xigioqcpjabpbdas
        ewkhuprpqzikmeop
        fgrgxsqeducigxvr
        bclkursnqkzmjihl
        jozidniwvnqhvsbc
        oghcilcyozrmmpta
        xbgmaungzcpasapi
        iqowypfiayzbcvhv
        opdehgwdgkocrgkf
        zfzvdjeinlegcjba
        vhakxvlcayuzukap
        xyradgyiebpevnwe
        eamhtflgedwyshkn
        igteqdgchjeulfth
        kwsfkigxzpbgdxod
        vapnpsbdboiewpzp
        wbuqhjsngxpqshen
        vxxilouxuytitwgm
        cpnwlkwnkeanqnet
        wdmbtqvvlowftvgb
        wjtmcecpyqzwpbqg
        jnxmoxdhvsphcdeg
        wabxfxpotoywwodn
        mwbsoxzlqpqobvvh
        coktshbyzjkxnwlt
        rzhnggpslwzvyqrp
        dgzuqbzarbutlkfx
        wunajaiiwgijfvjh
        uotdbcgmsvbsfqlb
        kxdtlgmqbccjqldb
        ngmjzjwvwbegehfr
        cvpsabqfpyygwncs
        wqluvqlhdhskgmzj
        rbveperybfntcfxs
        fbmoypqdyyvqyknz
        zxpgzwnvmuvkbgov
        yexcyzhyrpluxfbj
        ltqaihhstpzgyiou
        munhsdsfkjebdicd
        plecvjctydfbanep
        kjrxnnlqrpcieuwx
        zbcdtcqakhobuscf
        kgovoohchranhmsh
        llxufffkyvuxcmfx
        tgaswqyzqopfvxtw
        kojcqjkdpzvbtjtv
        xggdlkmkrsygzcfk
        vvitpsnjtdqwyzhh
        gcqjuwytlhxsecci
        vbsghygcsokphnrg
        vejqximdopiztjjm
        hudqtwmwkviiuslp
        vwswfvpcwwpxlyry
        gxmfiehdxptweweq
        qjmekjdcedfasopf
        pqyxdxtryfnihphf
        felnavctjjojdlgp
        hbimufguekgdxdac
        dhxhtnqgfczywxlr
        pssottpdjxkejjrh
        edieanguabapxyig
        sciinanyqblrbzbb
        irxpsorkpcpahiqi
        qsxecaykkmtfisei
        ivfwlvxlbnrzixff
        hqxzzfulfxpmivcw
        vvbpaepmhmvqykdg
        cetgicjasozykgje
        wuetifzdarhwmhji
        gaozwhpoickokgby
        eldnodziomvdfbuv
        favpaqktqaqgixtv
        twbcobsayaecyxvu
        lzyzjihydpfjgqev
        wnurwckqgufskuoh
        fxogtycnnmcbgvqz
        aetositiahrhzidz
        dyklsmlyvgcmtswr
        ykaxtdkjqevtttbx
        kfmnceyxyhiczzjm
        nnizopcndipffpko
        yjmznhzyfinpmvkb
        sljegcvvbnjhhwdd
        zmkeadxlwhfahpwg
        rwvcogvegcohcrmx
        aguqwrfymwbpscau
        vlusytjagzvsnbwe
        smvzhburcgvqtklh
        rfuprvjkhazrcxpv
        megqlnoqmymcrclc
        gvldhkewtmlwqvqv
        awynhvtyziemnjoa
        voprnvtnzspfvpeh
        dhlguqwmunbbekih
        goayirdhnjrfuiqi
        eoghydfykxdslohz
        chpippjykogxpbxq
        hqbycjweqczwjwgf
        pvefsrvwumrlvhmt
        eghwdovaynmctktk
        crwkxoucibumzawc
        bzbtahvhkdigvvtj
        bnbptgihhfubxhho
        ddqmbwyfmfnjjaro
        gvtswqyzazihctif
        vmqctjpgadxztqqb
        dgnndowtpeooaqqf
        sxdvctfdtalufxty
        ylgeexosibsmmckw
        sxplpyskbpqnojvw
        coarhxtsvrontyeg
        fyoaurggjupvzvlv
        jlyrkqsiwuggvjem
        uwbsjoxonreuucyi
        gihuqvwxovbgokes
        dxzaaxupbcgnxcwf
        gidrgmvyrlqqslve
        csflmlvqmonoywpx
        jkxkpixlythlacnk
        ejkarcdkdslldugv
        dbzmsusevohhjkmr
        cbrqzualjpdtworc
        kpgidqlmcbpfmmwu
        zwghjuofexfowqam
        ncdlxmcrsmsocetz
        kfprzqacefifjkbd
        swwzivrxulkhvldc
        wgqejhigbjwunscp
        rsstnwcyybfauqxu
        qhngfxyhdqopyfgk
        zrndpyyejsmqsiaj
        xxknxwpvafxiwwjc
        mmaahwgoiwbxloem
        tabacndyodmpuovp
        yriwomauudscvdce
        duvyscvfidmtcugl
        mgipxnqlfpjdilge
        imeeqcdetjuhfjnw
        dvkutrdofpulqkyh
        jefvtlktxegpmbya
        iyzudqgpvlzjfydh
        giohapxnpaqayryd
        qheqdprmnqlpztls
        rdxhijmzegxkotoq
        hdnmaspumdwnrcdz
        wafpbgehbuzdgsnc
        tbtrfztsferdmhsy
        vusndcyjngtkrtmk
        ilqblestzxebcifh
        urfgjbjgzlrfsdlv
        aptcdvpsqwleqttn
        bigczjvzokvfofiw
        zjnjeufonyqgkbpx
        trcdebioegfqrrdi
        jrdvdriujlmbqewt
        jqrcmuxpwurdhaue
        yjlermsgruublkly
        zwarvgszuqeesuwq
        xthhhqzwvqiyctvs
        mzwwaxnbdxhajyyv
        nclsozlqrjvqifyi
        gcnyqmhezcqvksqw
        deuakiskeuwdfxwp
        tclkbhqqcydlgrrl
        qbpndlfjayowkcrx
        apjhkutpoiegnxfx
        oaupiimsplsvcsie
        sdmxrufyhztxzgmt
        ukfoinnlbqrgzdeh
        azosvwtcipqzckns
        mydyeqsimocdikzn
        itfmfjrclmglcrkc
        swknpgysfscdrnop
        shyyuvvldmqheuiv
        tljrjohwhhekyhle
        dayinwzuvzimvzjw
        qgylixuuervyylur
        klqqaiemurawmaaz
        hdmzgtxxjabplxvf
        xiivzelzdjjtkhnj
        ktgplkzblgxwrnvo
        gvbpyofzodnknytd
        lqhlmnmhakqeffqw
        ltzdbngrcxwuxecy
        obxnfjeebvovjcjz
        zexpwallpocrxpvp
        tjpkkmcqbbkxaiak
        qiedfixxgvciblih
        qcxkhghosuslbyih
        gnsfidwhzaxjufgm
        xrghwgvyjakkzidw
        tftftwedtecglavz
        wquqczzkzqrlfngr
        twibtkijpvzbsfro
        bmplypdsvzuhrjxp
        zanrfmestvqpwbuh
        zonrhfqowyimcukm
        kpvajjfmqpbhrjma
        kujzluicngigjbtp
        iusguantsrwxdjal
        kwxeuylcnszswahw
        visdhnkobxnemldu
        rogeadmmaicwtabl
        pxqycifbgevqudvs
        osaiozyvlyddylqr
        vffjxrolrpuxcatx
        jbmsetccdrywssjd
        qgxyhjfpbfifmvgc
        npejgalglldxjdhs
        mbbtqgmttastrlck
        whapaqwdtpkropek
        dulbdboxazfyjgkg
        xaymnudlozbykgow
        lebvqmxeaymkkfoy
        bmicnfuubkregouj
        dieatyxxxlvhneoj
        yglaapcsnsbuvrva
        bbpjaslqpzqcwkpk
        xehuznbayagrbhnd
        ikqmeovaurmqfuvr
        ylyokwuzxltvxmgv
        hqtfinrkllhqtoiz
        pjmhtigznoaejifx
        fqdbmowkjtmvvrmx
        uvqtqfoulvzozfxv
        rpajajukuxtchrjd
        sznucejifktvxdre
        ufvibsmoushmjbne
        xirdqoshngthfvax
        iafpkddchsgdqmzl
        vmualmlduipvykzh
        fnmuahmblwyceejb
        ilsaapnswfoymiov
        lenvylifraahaclv
        cukqxlipuyxedqfh
        zgwecslpniqvtvuz
        cdcdfpsxuyrhsmag
        dszjinhantnxgqra
        ioimwotsgnjeacgt
        dqcymnvjystbynhp
        yibaudyfefbfgunx
        cabslcvunjavqkbf
        goymzvmgkvlsmugf
        zxteiitpthzskjjx
        agnxcnaqhjhlurzs
        cvmgyxhhnykuxbmb
        cgqmjexydmvgwxpp
        sygjajofieojiuna
        clpvxbrbjvqfbzvu
        cbntswqynsdqnhyv
        bztpbtwbefiotkfa
        pnxccbgajvhyeybu
        asyzrvgzumtuissa
        facjyblvcqqginxa
        rvwnucnbsvberxuv
        ghrbeykzrxclasie
        ekujtselepgjtaql
        krtrzsmduhsifyiw
        ticjswvsnyrwhpnt
        clmjhsftkfjzwyke
        lbxlcixxcztddlam
        xhfeekmxgbloguri
        azxqwlucwhahtvep
        kitdjrwmockhksow
        keznwwcusgbtvfrs
        ljvzxoywcofgwajj
        vebjnhnkcfzbhrcw
        eqfcxkavstxcuels
        ldattkyawjrvcido
        bsqqeilshcwtqyil
        foqqsxahfiozcqrw
        liswfmuhzfbyzjhf
        sulbdcyzmolapfbs
        zuggzkelwxjpsgxb
        betioxrgtnhpivcw
        xmtbixstdipibhgs
        ttvurgqmulryyaji
        viobnljznzppfmxw
        qlzabfopydtxrlet
        tusvydegfxhaxolk
        thoufvvfjferxhwp
        cfyyzppfarjiilbs
        jwmhxtgafkkgseqs
        pqwuuaxbeklodwpt
        vndyveahdiwgkjyx
        ssrjgasfhdouwyoh
        thbavfcisgvvyekf
        yjdvxmubvqadgypa
        tlbmcxaelkouhsvu
        bonohfnlboxiezzr
        rktlxcbkhewyvcjl
        rsmoutcbcssodvsc
        qszdratuxcrhsvoh
        eypyfahpuzqwzwhi
        yhkrleqmqlmwdnio
        vpnvxusvmngsobmq
        hkzyhopvxrsimzys
        dblriiwnrvnhxykl
        xkriqxkrprjwpncs
        rcymltrbszhyhqti
        mzbvneplsnpiztzn
        vkqtnptgbqefvfoc
        nwdtfiaozkcjtlax
        crximadpvdaccrsm
        lrbajafxwwnxvbei
        rbexzesrytpwwmjf
        stxwjarildpnzfpg
        btamaihdivrhhrrv
        acqbucebpaulpotl
        dkjhzghxxtxgdpvm
        rsbzwsnvlpqzyjir
        mizypbwvpgqoiams
        nvrslorjpqaasudn
        wvexcpzmconqkbvk
        rfwfumhjwzrvdzam
        eaghdaqorkhdsmth
        gtuntmpqaivosewh
        nzlsmdgjrigghrmy
        dhuvxwobpzbuwjgk
        kkcuvbezftvkhebf
        aeediumxyljbuyqu
        rfkpqeekjezejtjc
        wkzasuyckmgwddwy
        eixpkpdhsjmynxhi
        elrlnndorggmmhmx
        ayxwhkxahljoxggy
        mtzvvwmwexkberaw
        evpktriyydxvdhpx
        otznecuqsfagruls
        vrdykpyebzyblnut
        cnriedolerlhbqjy
        uajaprnrrkvggqgx
        xdlxuguloojvskjq
        mfifrjamczjncuym
        otmgvsykuuxrluky
        oiuroieurpyejuvm
    """.trimIndent()
