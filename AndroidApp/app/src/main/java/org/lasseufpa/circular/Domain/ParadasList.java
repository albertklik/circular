package org.lasseufpa.circular.Domain;

/**
 * Created by alberto on 02/04/2017.
 */

public abstract class ParadasList {


   // public static final int N_STOP_POINTS = 28;

    public static final double[][] POINTS = {

        { -1.47309 ,-48.45862,2},  //0- Transporte
        { -1.47552, -48.45836,9}, //1- Segurança
        { -1.47719,   -48.4581,21}, //2- RU
        { -1.47779,   -48.45676,35}, //3- MIRANTE
        { -1.47654,  -48.45487,44}, //4- VADIAO
        { -1.4749,  -48.45426,51}, //5- INCUBADORA
        {-1.47386,  -48.45463,55}, //6- ARTE
        { -1.47289,  -48.45158,80}, //7- TERMINAL
        { -1.47157,-48.44981,96}, //8- JURIDICO (NAEA)
        { -1.47293,   -48.44877,105}, //9- ICSA/ICJ
        { -1.47148,  -48.448,115}, //10- ODONTOLOGIA
        {  -1.47008,-48.44669,127}, //11 - NUTRIÇÃO
        { -1.46972,  -48.44655}, //12- BETTINA
        { -1.46846,-48.44822,184}, //13- GENOMA
        {-1.46436,-48.44475,232}, //14- INOVACAO
        {-1.46159,-48.44209,291}, //15- INPE

            //VOLTA

        { -1.46436,-48.44475,350}, //16- INOVACAO
        { -1.46846,-48.44822,398}, //17 - GENOMA
     //   { -1.471116,  -48.448037,0}, //18- BETTINA
    //    { -1.470950,  -48.447908,0}, //19- NUTRICAO
        {  -1.47148,-48.448,407}, //20- ODONTOLOGIA
     //   { -1.467505,  -48.447569,0}, //21- ICSA/ICJ
     //   { -1.467192,  -48.446933,0}, //22- JURIDICO/NAIE
    //    { -1.468420,  -48.448159,0}, //23- TERMINAL
    //    { -1.468937,  -48.448218,0}, //24- ARTE
    //    { -1.463631,  -48.444562,0}, //25- INCUBADORA
     //   { -1.463215,  -48.444622,0}, //26- VADIAO
            { -1.47622,-48.45562,484}, //27- REITORIA
            { -1.4744,-48.45583,488}, //28 - ICEN
           { -1.47297,-48.45598,496}, //29 - GINASIO
            {-1.47243,-48.45728,499} //30 - CAPACIT




    };

    public static final String[][] NAME_DESCRIPTION = {

            //IDA
    {"1-Transporte"          , "Departamento de transporte da UFPA"                 },        //0
    {"2-Segurança"    , "Segurança Universidade Federal do Pará"                    },        //1
    {"3-R.U."      , "Restaurante Universitário"                                    },        //2
    {"4-Mirante", "Bloco de Aulas Mirante do Rio"                                   },        //3
    {"5-Vadião", "Espaço de Recreação, Bancos, Lojas e Serviços"                    },        //4
    {"6-Incubadora", "Universitec, PPGITEC, Newton"                                 },        //5
    {"7-Arte", "Instituto de Ciências da arte / Espaço verde do ITEC"               },        //6
    {"8-Terminal", "Terminal Rodoviário, Portão 3 da UFPA"                          },        //7
    {"9-Juridico (NAEA)", "ICJ, NAEA"                                               },        //8
    {"10-ICSA, ICJ", "Instituto de ciências sociais aplicadas"                      },        //9
    {"11-Odontologia", "Faculdade de Odontologia"                                   },        //10
    {"12-Nutrição", "Enfermagem"                                                    },        //11
    {"13-Bettina", "Hospital bettina ferro de souza"                                },        //12
    {"14-Genoma", "Bloco de Pós Graduação do ITEC"                                  },        //13
    {"15-Espaço Inovação", "Parque de Ciência e Tecnologia da UFPA"                 },        //14
     {"16-INPE", "Instituto de Pesquisas Espaciais"                                 },        //15


            //VOLTA

    {"17-Espaço Inovação", "Parque de Ciência e Tecnologia da UFPA"                                                             },        //16
    {"18-Genoma", "Bloco de Pós Graduação do ITEC"                      },        //17
 //   {"#19-Bettina", "Hospital bettina ferro de souza"                          },        //18
 //   {"#20-Nutrição", "Enfermagem"                       },        //19
    {"21-Odontologia", "Faculdade de Odontologia"                                  },        //20
 //   {"22-Portão 4", "Portão 4 da UFPA"                                             },        //21
 //   {"23-CEAMAZON", "Centro de Exc. em Eficiência energética da Amazônia "         },        //22
 //   {"24-Biomedicina", ""                                                          },        //23
 //   {"25-Parada Circular", ""                                                      },        //24
 //   {"26-PCT guamá", "Parque de ciência e Tecnologia"                              },        //25
 //   {"27-PCT guamá", "Parque de Ciência e Tecnologia"                              },        //26
      {"28-Reitoria", "Reitoria da Universidade Federal do Pará"                     },         //27
            {"29-ICEN", "Instituto de ciências Exatas e Naturais"                    },         //28
            {"30-Ginásio", "Ginásio de Esportes da Ufpa"                             },         //29
            {"31-CAPACIT", "Centro de capacitação e desenvolvimento"                 }         //30

    };





}
