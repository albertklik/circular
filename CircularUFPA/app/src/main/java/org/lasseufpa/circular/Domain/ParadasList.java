package org.lasseufpa.circular.Domain;

/**
 * Created by alberto on 02/04/2017.
 */

public abstract class ParadasList {


   // public static final int N_STOP_POINTS = 28;

    public static final int INDEX_POINTS_LATITUDE = 0;
    public static final int INDEX_POINTS_LONGITUDE = 1;
    public static final int INDEX_POINTS_ID_ROTA = 2;
    public static final int INDEX_POINTS_INSERIR_MAPA = 3;
    public static final int INDEX_POINTS_TIPO = 4;
    public static final int INDEX_SENTIDO_ROTA = 5;

    public static final int TIPO_INICIO = 1;
    public static final int TIPO_PARDA = 2;
    public static final int TIPO_FINAL = 3;

    public static final int ROTA_IDA = 1;
    public static final int ROTA_VOLTA = 2;

    public static final double[][] POINTS = {


        //{latitude,longitude,correspondencia_rota,inserir_mapa,tipo,sentido_rota)

        {         0,        0,0,0,1,1}, //0 - INICIO ROTA IDA
        { -1.47309 ,-48.45862,2,1,2,1},  //1- Transporte
        { -1.47552, -48.45836,9,1,2,1}, //2- Segurança
        { -1.47719,   -48.4581,21,1,2,1}, //3- RU
        { -1.47779,   -48.45676,35,1,2,1}, //4- MIRANTE
        { -1.47654,  -48.45487,44,1,2,1}, //5- VADIAO
        { -1.4749,  -48.45426,51,1,2,1}, //6- INCUBADORA
        {-1.47386,  -48.45463,55,1,2,1}, //7- ARTE
        { -1.47289,  -48.45158,80,1,2,1}, //8- TERMINAL
        { -1.47157,-48.44981,96,1,2,1}, //9- JURIDICO (NAEA)
        { -1.47293,   -48.44877,105,1,2,1}, //10- ICSA/ICJ
        { -1.47148,  -48.448,115,1,2,1}, //11- ODONTOLOGIA
        {  -1.47008,-48.44669,127,1,2,1}, //12 - NUTRIÇÃO
        { -1.46972,  -48.44655,127,1,2,1}, //13- BETTINA
        { -1.46846,-48.44822,184,1,2,1}, //14- GENOMA
        {-1.46436,-48.44475,232,1,2,1}, //15- INOVACAO
        {-1.46159,-48.44209,291,1,2,1}, //16- INPE
        {       0,        0,  0,0,3,1}, //17 - FIM ROTA IDA

            //VOLTA

        {        0,        0,  0,0,1,2}, //18 - INICIO ROTA VOLTA
        { -1.46436,-48.44475,350,1,2,2}, //19- INOVACAO
        { -1.46846,-48.44822,398,1,2,2}, //20 - GENOMA
        {  -1.47148,-48.448,407,1,2,2},   //21- ODONTOLOGIA
        { -1.47293, -48.44877,105,0,2,2}, //24- ICSA/ICJ
        { -1.47157,-48.44981,96,0,2,2},   //25- JURIDICO/NAIE
        { -1.47289,  -48.45158,80,0,2,2}, //26- TERMINAL
        {-1.47386,  -48.45463,55,1,2,2},  //27- ARTE
        { -1.4749,  -48.45426,51,1,2,2},  //28- INCUBADORA
        { -1.47654,  -48.45487,44,1,2,2}, //29- VADIAO
        { -1.47622,-48.45562,484,1,2,2},  //28- REITORIA
        { -1.4744,-48.45583,488,1,2,2},   //29 - ICEN
        { -1.47297,-48.45598,496,1,2,2},  //30 - GINASIO
        {-1.47243,-48.45728,499,1,2,2},   //31 - CAPACIT
        {       0,        0,  0,0,3,2}    //32 - FIM DA ROTA VOLTA



    };

    public static final String[][] NAME_DESCRIPTION = {

            //IDA
    {"Rota de Ida"          , "Início no departamento de transporte da UFPA"        },        //0
    {"1-Transporte"          , "Departamento de transporte da UFPA"                 },        //1
    {"2-Segurança"    , "Segurança Universidade Federal do Pará"                    },        //2
    {"3-R.U."      , "Restaurante Universitário"                                    },        //3
    {"4-Mirante", "Bloco de Aulas Mirante do Rio"                                   },        //4
    {"5-Vadião", "Espaço de Recreação, Bancos, Lojas e Serviços"                    },        //5
    {"6-Incubadora", "Universitec, PPGITEC, Newton"                                 },        //6
    {"7-Arte", "Instituto de Ciências da arte / Espaço verde do ITEC"               },        //7
    {"8-Terminal", "Terminal Rodoviário, Portão 3 da UFPA"                          },        //8
    {"9-Juridico (NAEA)", "ICJ, NAEA"                                               },        //9
    {"10-ICSA, ICJ", "Instituto de ciências sociais aplicadas"                      },        //10
    {"11-Odontologia", "Faculdade de Odontologia"                                   },        //11
    {"12-Nutrição", "Enfermagem"                                                    },        //12
    {"13-Bettina", "Hospital bettina ferro de souza"                                },        //13
    {"14-Genoma", "Bloco de Pós Graduação do ITEC"                                  },        //14
    {"15-Espaço Inovação", "Parque de Ciência e Tecnologia da UFPA"                 },        //15
    {"16-INPE", "Instituto de Pesquisas Espaciais"                                  },        //16
    {"Fim da rota", "Finaliza no centro de Pesquisas Espaciais"                     },        //17


            //VOLTA
    {"Rota de Volta", "Início no centro de pesquisas Espaciais - INPE"              },        //18
    {"17-Espaço Inovação", "Parque de Ciência e Tecnologia da UFPA"                 },        //19
    {"18-Genoma", "Bloco de Pós Graduação do ITEC"                                  },        //20
    {"19-Odontologia", "Faculdade de Odontologia"                                   },        //21
    {"20-ICSA, ICJ", "Instituto de ciências sociais aplicadas"                      },        //22
    {"21-Juridico (NAEA)", "ICJ, NAEA"                                              },        //23
    {"22-Terminal", "Terminal Rodoviário, Portão 3 da UFPA"                         },        //24
    {"23-Arte", "Instituto de Ciências da arte / Espaço verde do ITEC"              },        //25
    {"24-Incubadora", "Universitec, PPGITEC, Newton"                                },        //26
    {"25-Vadião", "Espaço de Recreação, Bancos, Lojas e Serviços"                   },        //27
    {"26-Reitoria", "Reitoria da Universidade Federal do Pará"                      },        //28
    {"27-ICEN", "Instituto de ciências Exatas e Naturais"                           },        //29
    {"28-Ginásio", "Ginásio de Esportes da Ufpa"                                    },        //30
    {"29-CAPACIT", "Centro de capacitação e desenvolvimento"                        },        //31
    {"fim da rota", "Finaliza no departamento de transporte da ufpa"                }         //32

    };





}
