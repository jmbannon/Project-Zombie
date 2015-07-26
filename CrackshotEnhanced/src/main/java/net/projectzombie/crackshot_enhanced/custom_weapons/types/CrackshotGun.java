/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.FireMode.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Scope.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Attatchment.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotBase.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunAccess;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunUtils;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jbannon
 */
public enum CrackshotGun implements Type
{
    //SWITCH AUG AND SG TO ASSULT; SWITCH G3 TO AUTO SNIP
    
//  Name        GunID, Type, Fire, Sight, Supp, CSname, BSpread
    
    /* PISTOLS ************************************************************************************************************************************/
    I_Deagle    (0,  DEAGLE, SEMI,  IRON, NA,  "Deagle",   1.101),
    A_Deagle    (30, DEAGLE, SEMI,  ACOG, NA,  "A_Deagle", 1.101),
    
    I_Colt45    (1,  COLT45, SEMI,  IRON, NA, "Colt45",   1.40),
    A_Colt45    (31, COLT45, SEMI,  ACOG, NA, "A_Colt45", 1.40),
    
    I___P228    (2,  P228, SEMI,  IRON, NA,  "P228",    1.09),
    A___P228    (32, P228, SEMI,  ACOG, NA,  "A_P228",  1.09),
    IB__P228    (33, P228, BURST, IRON, NA,  "B_P228",  1.09),
    AB__P228    (34, P228, BURST, ACOG, NA,  "AB_P228", 1.09),
    I_S_P228    (35, P228, SEMI,  IRON, SUP, "S_P228",  1.19),
    A_S_P228    (36, P228, SEMI,  ACOG, SUP, "SA_P228", 1.19),
    IBS_P228    (37, P228, BURST, IRON, SUP, "SB_P228", 1.19),
    ABS_P228    (38, P228, BURST, ACOG, SUP, "SAB_P228",1.19),
    
    I__USP      (3,  USP, SEMI,  IRON, SUP, "USP",    1.32),
    A__USP      (39, USP, SEMI,  ACOG, SUP, "A_USP",  1.32),
    IB_USP      (40, USP, BURST, IRON, SUP, "B_USP",  1.32),
    AB_USP      (41, USP, BURST, ACOG, SUP, "AB_USP", 1.32),
    
    /* REVOLVERS **********************************************************************************************************************************/
    Mag44       (4, MAG44, SEMI, IRON, NA, "44Mag",      0.78),
    DirtyFrank  (5, FRANK, SEMI, ACOG, NA, "DirtyFrank", 0.50),
    
    /* SNIPERS ************************************************************************************************************************************/
    L__Scout     (6,  SCOUT, BOLT, LONG, NA,  "Scout",    0.28),
    T__Scout     (42, SCOUT, BOLT, TACT, NA,  "T_Scout",  0.33),
    A__Scout     (43, SCOUT, BOLT, ACOG, NA,  "A_Scout",  0.38),
    I__Scout     (44, SCOUT, BOLT, IRON, NA,  "I_Scout",  0.43),
    LS_Scout     (45, SCOUT, BOLT, LONG, SUP, "LS_Scout", 0.33),
    TS_Scout     (46, SCOUT, BOLT, TACT, SUP, "TS_Scout", 0.38),
    AS_Scout     (47, SCOUT, BOLT, ACOG, SUP, "AS_Scout", 0.43),
    IS_Scout     (48, SCOUT, BOLT, IRON, SUP, "IS_Scout", 0.48),
    
    //TODO
    L_AWP         (8,  AWP, BOLT, LONG, NA, "AWP",   0.16),
    T_AWP         (49, AWP, BOLT, TACT, NA, "T_AWP", 0.21),
    A_AWP         (50, AWP, BOLT, ACOG, NA, "A_AWP", 0.26),
    I_AWP         (51, AWP, BOLT, IRON, NA, "I_AWP", 0.31),
    
    /* AUTO-SNIPERS *******************************************************************************************************************************/
    LS_G3SG1       (19, G3SG1, SEMI, LONG, SUP, "G3SG1",    0.48),
    TS_G3SG1       (52, G3SG1, SEMI, TACT, SUP, "TS_G3SG1", 0.48),
    AS_G3SG1       (53, G3SG1, SEMI, ACOG, SUP, "AS_G3SG1", 0.48),
    IS_G3SG1       (54, G3SG1, SEMI, IRON, SUP, "IS_G3SG1", 0.48),   
    L__G3SG1       (55, G3SG1, SEMI, LONG, NA,  "L_G3SG1",  0.48),
    T__G3SG1       (56, G3SG1, SEMI, TACT, NA,  "T_G3SG1",  0.48),
    A__G3SG1       (57, G3SG1, SEMI, ACOG, NA,  "A_G3SG1",  0.48),
    I__G3SG1       (58, G3SG1, SEMI, IRON, NA,  "I_G3SG1",  0.48),
    
    L_Dragonuv    (7,  DRAG, SEMI, LONG, NA, "Dragonuv",   0.46),
    T_Dragonuv    (59, DRAG, SEMI, TACT, NA, "T_Dragonuv", 0.46),
    A_Dragonuv    (60, DRAG, SEMI, ACOG, NA, "A_Dragonuv", 0.46),
    I_Dragonuv    (61, DRAG, SEMI, IRON, NA, "I_Dragonuv", 0.46),
    
    /* HUNTING RIFLES *****************************************************************************************************************************/
    L_Remington   (9,  REMINGTON, BOLT, LONG, NA, "Remington700",   0.26),
    T_Remington   (62, REMINGTON, BOLT, TACT, NA, "T_Remington700", 0.26),
    A_Remington   (63, REMINGTON, BOLT, ACOG, NA, "A_Remington700", 0.26),
    I_Remington   (64, REMINGTON, BOLT, IRON, NA, "I_Remington700", 0.26),
    
    I_Springfield (10, SPRINGFIELD, BOLT, IRON, NA, "M1903",   0.52),
    A_Springfield (65, SPRINGFIELD, BOLT, ACOG, NA, "A_M1903", 0.52),
    T_Springfield (66, SPRINGFIELD, BOLT, TACT, NA, "T_M1903", 0.52),
    L_Springfield (67, SPRINGFIELD, BOLT, LONG, NA, "L_M1903", 0.52),
    
    L_Model70 (11,  MODEL70, BOLT, LONG, NA, "Model70",   0.41),
    T_Model70 (68,  MODEL70, BOLT, TACT, NA, "T_Model70", 0.41),
    A_Model70 (69,  MODEL70, BOLT, ACOG, NA, "A_Model70", 0.41),
    I_Model70 (70,  MODEL70, BOLT, IRON, NA, "I_Model70", 0.41),
    
    /* SMGS ***************************************************************************************************************************************/
    IA__Mac10 (12, MAC10, AUTO,  IRON, NA,  "Mac10",     1.25),
    AA__Mac10 (71, MAC10, AUTO,  ACOG, NA,  "AA_Mac10",  1.25), 
    IB__Mac10 (72, MAC10, BURST, IRON, NA,  "IB_Mac10",  1.25),
    AB__Mac10 (73, MAC10, BURST, ACOG, NA,  "AB_Mac10",  1.25),
    IS__Mac10 (74, MAC10, SEMI,  IRON, NA,  "IS_Mac10",  1.25),
    AS__Mac10 (75, MAC10, SEMI,  ACOG, NA,  "AS_Mac10",  1.25),
    IAS_Mac10 (76, MAC10, AUTO,  IRON, SUP, "IAS_Mac10", 1.25),
    AAS_Mac10 (77, MAC10, AUTO,  ACOG, SUP, "AAS_Mac10", 1.25),
    IBS_Mac10 (78, MAC10, BURST, IRON, SUP, "IBS_Mac10", 1.25),
    ABS_Mac10 (79, MAC10, BURST, ACOG, SUP, "ABS_Mac10", 1.25),
    ISS_Mac10 (80, MAC10, SEMI,  IRON, SUP, "ISS_Mac10", 1.25),
    ASS_Mac10 (81, MAC10, SEMI,  ACOG, SUP, "ASS_Mac10", 1.25),
    
    IA__Ump45 (13, UMP45, AUTO,  IRON, NA,  "Ump45",     0.99),
    AA__Ump45 (82, UMP45, AUTO,  ACOG, NA,  "AA_Ump45",  0.99),
    IB__Ump45 (83, UMP45, BURST, IRON, NA,  "IB_Ump45",  0.99),
    AB__Ump45 (84, UMP45, BURST, ACOG, NA,  "AB_Ump45",  0.99),
    IS__Ump45 (85, UMP45, SEMI,  IRON, NA,  "IS_Ump45",  0.99),
    AS__Ump45 (86, UMP45, SEMI,  ACOG, NA,  "AS_Ump45",  0.99),
    IAS_Ump45 (87, UMP45, AUTO,  IRON, SUP, "IAS_Ump45", 0.99),
    AAS_Ump45 (88, UMP45, AUTO,  ACOG, SUP, "AAS_Ump45", 0.99),
    IBS_Ump45 (89, UMP45, BURST, IRON, SUP, "IBS_Ump45", 0.99),
    ABS_Ump45 (90, UMP45, BURST, ACOG, SUP, "ABS_Ump45", 0.99),
    ISS_Ump45 (91, UMP45, SEMI,  IRON, SUP, "ISS_Ump45", 0.99),
    ASS_Ump45 (92, UMP45, SEMI,  ACOG, SUP, "ASS_Ump45", 0.99),
    
    IA_P90 (14, P90, AUTO,  IRON, NA, "P90",    1.16),
    AA_P90 (93, P90, AUTO,  ACOG, NA, "AA_P90", 1.16),
    IB_P90 (94, P90, BURST, IRON, NA, "IB_P90", 1.16),
    AB_P90 (95, P90, BURST, ACOG, NA, "AB_P90", 1.16),
    IS_P90 (96, P90, SEMI,  IRON, NA, "IS_P90", 1.16),
    AS_P90 (97, P90, SEMI,  ACOG, NA, "AS_P90", 1.16),
    
    IS__MP5 (15,  MP5, SEMI,  IRON, NA,  "MP5",      0.93),
    AS__MP5 (98,  MP5, SEMI,  ACOG, NA,  "AS_MP5",   0.93),
    TS__MP5 (99,  MP5, SEMI,  TACT, NA,  "TS_MP5",   0.93),
    IB__MP5 (100, MP5, BURST, IRON, NA,  "IB_MP5",   0.93),
    AB__MP5 (101, MP5, BURST, ACOG, NA,  "AB_MP5",  0.93),
    TB__MP5 (102, MP5, BURST, TACT, NA,  "TB_MP5",  0.93),
    IA__MP5 (103, MP5, AUTO,  IRON, NA,  "TA_MP5",  0.93),
    AA__MP5 (104, MP5, AUTO,  ACOG, NA,  "AA_MP5",  0.93),
    TA__MP5 (105, MP5, AUTO,  TACT, NA,  "TA_MP5",  0.93),
    ISS_MP5 (106, MP5, SEMI,  IRON, SUP, "ISS_MP5", 0.93),
    ASS_MP5 (107, MP5, SEMI,  ACOG, SUP, "ASS_MP5", 0.93),
    TSS_MP5 (108, MP5, SEMI,  TACT, SUP, "TSS_MP5", 0.93),
    IBS_MP5 (109, MP5, BURST, IRON, SUP, "IBS_MP5", 0.93),
    ABS_MP5 (110, MP5, BURST, ACOG, SUP, "ABS_MP5", 0.93),
    TBS_MP5 (111, MP5, BURST, TACT, SUP, "TBS_MP5", 0.93),
    IAS_MP5 (112, MP5, AUTO,  IRON, SUP, "IAS_MP5", 0.93),
    AAS_MP5 (113, MP5, AUTO,  ACOG, SUP, "AAS_MP5", 0.93),
    TAS_MP5 (114, MP5, AUTO,  TACT, SUP, "TAS_MP5", 0.93),
    
    IA_TMP (16,  TMP, AUTO,  IRON, SUP, "TMP",    1.05),
    AA_TMP (115, TMP, AUTO,  ACOG, SUP, "AA_TMP", 1.05),
    IB_TMP (116, TMP, BURST, IRON, SUP, "IB_TMP", 1.05),
    AB_TMP (117, TMP, BURST, ACOG, SUP, "AB_TMP", 1.05),
    IS_TMP (118, TMP, SEMI,  IRON, SUP, "IS_TMP", 1.05),
    AS_TMP (119, TMP, SEMI,  ACOG, SUP, "AS_TMP", 1.05),
    
    /* ASSAULT RIFLES *****************************************************************************************************************************/
    AA_SG552  (17,  SG552, AUTO,  ACOG, NA,  "SG552",     0.61),
    IA_SG552  (120, SG552, AUTO,  IRON, NA,  "IA_SG552",  0.61),
    TA_SG552  (121, SG552, AUTO,  TACT, NA,  "TA_SG552",  0.61),
    AB_SG552  (122, SG552, BURST, ACOG, NA,  "AB_SG552",  0.61),
    IB_SG552  (123, SG552, BURST, IRON, NA,  "IB_SG552",  0.61),
    TB_SG552  (124, SG552, BURST, TACT, NA,  "TB_SG552",  0.61),
    AS_SG552  (125, SG552, SEMI,  ACOG, NA,  "AS_SG552",  0.61),
    IS_SG552  (126, SG552, SEMI,  IRON, NA,  "IS_SG552",  0.61),
    TS_SG552  (127, SG552, SEMI,  TACT, NA,  "TS_SG552",  0.61),
    AAB_SG552 (128, SG552, AUTO,  ACOG, EXT, "AAB_SG552", 0.61),
    IAB_SG552 (129, SG552, AUTO,  IRON, EXT, "IAB_SG552", 0.61),
    TAB_SG552 (130, SG552, AUTO,  TACT, EXT, "TAB_SG552", 0.61),
    ABB_SG552 (131, SG552, BURST, ACOG, EXT, "ABB_SG552", 0.61),
    IBB_SG552 (132, SG552, BURST, IRON, EXT, "IBB_SG552", 0.61),
    TBB_SG552 (133, SG552, BURST, TACT, EXT, "TBB_SG552", 0.61),
    ASB_SG552 (134, SG552, SEMI,  ACOG, EXT, "ASB_SG552", 0.61),
    ISB_SG552 (135, SG552, SEMI,  IRON, EXT, "ISB_SG552", 0.61),
    TSB_SG552 (136, SG552, SEMI,  TACT, EXT, "TSB_SG552", 0.61),
    AAR_SG552 (137, SG552, AUTO,  ACOG, REL, "AAR_SG552", 0.61),
    IAR_SG552 (138, SG552, AUTO,  IRON, REL, "IAR_SG552", 0.61),
    TAR_SG552 (139, SG552, AUTO,  TACT, REL, "TAR_SG552", 0.61),
    ABR_SG552 (140, SG552, BURST, ACOG, REL, "ABR_SG552", 0.61),
    IBR_SG552 (141, SG552, BURST, IRON, REL, "IBR_SG552", 0.61),
    TBR_SG552 (142, SG552, BURST, TACT, REL, "TBR_SG552", 0.61),
    ASR_SG552 (143, SG552, SEMI,  ACOG, REL, "ASR_SG552", 0.61),
    ISR_SG552 (144, SG552, SEMI,  IRON, REL, "ISR_SG552", 0.61),
    TSR_SG552 (145, SG552, SEMI,  TACT, REL, "TSR_SG552", 0.61),
    
    AA_AUG  (18,  AUG, AUTO,  ACOG, NA,  "AUG",     0.55),
    IA_AUG  (146, AUG, AUTO,  IRON, NA,  "IA_AUG",  0.55),
    TA_AUG  (147, AUG, AUTO,  TACT, NA,  "TA_AUG",  0.55),
    LA_AUG  (148, AUG, AUTO,  LONG, NA,  "LA_AUG",  0.55),
    AB_AUG  (149, AUG, BURST, ACOG, NA,  "AB_AUG",  0.55),
    IB_AUG  (150, AUG, BURST, IRON, NA,  "IB_AUG",  0.55),
    TB_AUG  (151, AUG, BURST, TACT, NA,  "TB_AUG",  0.55),
    LB_AUG  (152, AUG, BURST, LONG, NA,  "LB_AUG",  0.55),
    AS_AUG  (153, AUG, SEMI,  ACOG, NA,  "AS_AUG",  0.55),
    IS_AUG  (154, AUG, SEMI,  IRON, NA,  "IS_AUG",  0.55),
    TS_AUG  (155, AUG, SEMI,  TACT, NA,  "TS_AUG",  0.55),
    LS_AUG  (156, AUG, SEMI,  LONG, NA,  "LS_AUG",  0.55),
    AAB_AUG (157, AUG, AUTO,  ACOG, EXT, "AAB_AUG", 0.55),
    IAB_AUG (158, AUG, AUTO,  IRON, EXT, "IAB_AUG", 0.55),
    TAB_AUG (159, AUG, AUTO,  TACT, EXT, "TAB_AUG", 0.55),
    LAB_AUG (160, AUG, AUTO,  LONG, EXT, "LAB_AUG", 0.55),
    ABB_AUG (161, AUG, BURST, ACOG, EXT, "ABB_AUG", 0.55),
    IBB_AUG (162, AUG, BURST, IRON, EXT, "IBB_AUG", 0.55),
    TBB_AUG (163, AUG, BURST, TACT, EXT, "TBB_AUG", 0.55),
    LBB_AUG (164, AUG, BURST, LONG, EXT, "LBB_AUG", 0.55),
    ASB_AUG (165, AUG, SEMI,  ACOG, EXT, "ASB_AUG", 0.55),
    ISB_AUG (166, AUG, SEMI,  IRON, EXT, "ISB_AUG", 0.55),
    TSB_AUG (167, AUG, SEMI,  TACT, EXT, "TSB_AUG", 0.55),
    LSB_AUG (168, AUG, SEMI,  LONG, EXT, "LSB_AUG", 0.55),
    AAR_AUG (169, AUG, AUTO,  ACOG, REL, "AAR_AUG", 0.55),
    IAR_AUG (170, AUG, AUTO,  IRON, REL, "IAR_AUG", 0.55),
    TAR_AUG (171, AUG, AUTO,  TACT, REL, "TAR_AUG", 0.55),
    LAR_AUG (172, AUG, AUTO,  LONG, REL, "LAR_AUG", 0.55),
    ABR_AUG (173, AUG, BURST, ACOG, REL, "ABR_AUG", 0.55),
    IBR_AUG (174, AUG, BURST, IRON, REL, "IBR_AUG", 0.55),
    TBR_AUG (175, AUG, BURST, TACT, REL, "TBR_AUG", 0.55),
    LBR_AUG (176, AUG, BURST, LONG, REL, "LBR_AUG", 0.55),
    ASR_AUG (177, AUG, SEMI,  ACOG, REL, "ASR_AUG", 0.55),
    ISR_AUG (178, AUG, SEMI,  IRON, REL, "ISR_AUG", 0.55),
    TSR_AUG (179, AUG, SEMI,  TACT, REL, "TSR_AUG", 0.55),
    LSR_AUG (180, AUG, SEMI,  LONG, REL, "LSR_AUG", 0.55),
    AAS_AUG (242, AUG, AUTO,  ACOG, SUP, "AAS_AUG", 0.55),
    IAS_AUG (243, AUG, AUTO,  IRON, SUP, "IAS_AUG", 0.55),
    TAS_AUG (244, AUG, AUTO,  TACT, SUP, "TAS_AUG", 0.55),
    LAS_AUG (245, AUG, AUTO,  LONG, SUP, "LAS_AUG", 0.55),
    ABS_AUG (246, AUG, BURST, ACOG, SUP, "ABS_AUG", 0.55),
    IBS_AUG (247, AUG, BURST, IRON, SUP, "IBS_AUG", 0.55),
    TBS_AUG (248, AUG, BURST, TACT, SUP, "TBS_AUG", 0.55),
    LBS_AUG (249, AUG, BURST, LONG, SUP, "LBS_AUG", 0.55),
    ASS_AUG (250, AUG, SEMI,  ACOG, SUP, "ASS_AUG", 0.55),
    ISS_AUG (251, AUG, SEMI,  IRON, SUP, "ISS_AUG", 0.55),
    TSS_AUG (252, AUG, SEMI,  TACT, SUP, "TSS_AUG", 0.55),
    LSS_AUG (253, AUG, SEMI,  LONG, SUP, "LSS_AUG", 0.55),
    
    IA_AK47  (20,  AK47, AUTO,  IRON, NA,  "AK-47",     0.765),
    AA_AK47  (181, AK47, AUTO,  ACOG, NA,  "AA_AK-47",  0.765),
    TA_AK47  (182, AK47, AUTO,  TACT, NA,  "TA_AK-47",  0.765),
    IB_AK47  (183, AK47, BURST, IRON, NA,  "IB_AK-47",  0.765),
    AB_AK47  (184, AK47, BURST, ACOG, NA,  "AB_AK-47",  0.765),
    TB_AK47  (185, AK47, BURST, TACT, NA,  "TB_AK-47",  0.765),
    IS_AK47  (186, AK47, SEMI,  IRON, NA,  "IS_AK-47",  0.765),
    AS_AK47  (187, AK47, SEMI,  ACOG, NA,  "AS_AK-47",  0.765),
    TS_AK47  (188, AK47, SEMI,  TACT, NA,  "TS_AK-47",  0.765),
    IAE_AK47 (189, AK47, AUTO,  IRON, EXT, "IAE_AK-47", 0.765),
    AAE_AK47 (190, AK47, AUTO,  ACOG, EXT, "AAE_AK-47", 0.765),
    TAE_AK47 (191, AK47, AUTO,  TACT, EXT, "TAE_AK-47", 0.765),
    IBE_AK47 (192, AK47, BURST, IRON, EXT, "IBE_AK-47", 0.765),
    ABE_AK47 (193, AK47, BURST, ACOG, EXT, "ABE_AK-47", 0.765),
    TBE_AK47 (194, AK47, BURST, TACT, EXT, "TBE_AK-47", 0.765),
    ISE_AK47 (195, AK47, SEMI,  IRON, EXT, "ISE_AK-47", 0.765),
    ASE_AK47 (196, AK47, SEMI,  ACOG, EXT, "ASE_AK-47", 0.765),
    TSE_AK47 (197, AK47, SEMI,  TACT, EXT, "TSE_AK-47", 0.765),
    IAR_AK47 (198, AK47, AUTO,  IRON, REL, "IAR_AK-47", 0.765),
    AAR_AK47 (199, AK47, AUTO,  ACOG, REL, "AAR_AK-47", 0.765),
    TAR_AK47 (200, AK47, AUTO,  TACT, REL, "TAR_AK-47", 0.765),
    IBR_AK47 (201, AK47, BURST, IRON, REL, "IBR_AK-47", 0.765),
    ABR_AK47 (202, AK47, BURST, ACOG, REL, "ABR_AK-47", 0.765),
    TBR_AK47 (203, AK47, BURST, TACT, REL, "TBR_AK-47", 0.765),
    ISR_AK47 (204, AK47, SEMI,  IRON, REL, "ISR_AK-47", 0.765),
    ASR_AK47 (205, AK47, SEMI,  ACOG, REL, "ASR_AK-47", 0.765),
    TSR_AK47 (206, AK47, SEMI,  TACT, REL, "TSR_AK-47", 0.765),
    
    IB_M16  (21,  M16, BURST, IRON, NA,  "M16",     0.598),
    AB_M16  (207, M16, BURST, ACOG, NA,  "AB_M16",  0.598),
    TB_M16  (208, M16, BURST, TACT, NA,  "TB_M16",  0.598),
    IS_M16  (209, M16, SEMI,  IRON, NA,  "IS_M16",  0.598),
    AS_M16  (210, M16, SEMI,  ACOG, NA,  "AS_M16",  0.598),
    TS_M16  (211, M16, SEMI,  TACT, NA,  "TS_M16",  0.598),
    IA_M16  (212, M16, AUTO,  IRON, NA,  "IA_M16",  0.598),
    AA_M16  (213, M16, AUTO,  ACOG, NA,  "AA_M16",  0.598),
    TA_M16  (214, M16, AUTO,  TACT, NA,  "TA_M16",  0.598),
    IBS_M16 (215, M16, BURST, IRON, SUP, "IBS_M16", 0.598),
    ABS_M16 (216, M16, BURST, ACOG, SUP, "ABS_M16", 0.598),
    TBS_M16 (217, M16, BURST, TACT, SUP, "TBS_M16", 0.598),
    ISS_M16 (218, M16, SEMI,  IRON, SUP, "ISS_M16", 0.598),
    ASS_M16 (219, M16, SEMI,  ACOG, SUP, "ASS_M16", 0.598),
    TSS_M16 (220, M16, SEMI,  TACT, SUP, "TSS_M16", 0.598),
    IAS_M16 (221, M16, AUTO,  IRON, SUP, "IAS_M16", 0.598),
    AAS_M16 (222, M16, AUTO,  ACOG, SUP, "AAS_M16", 0.598),
    TAS_M16 (223, M16, AUTO,  TACT, SUP, "TAS_M16", 0.598),
    IBR_M16 (224, M16, BURST, IRON, REL, "IBR_M16", 0.598),
    ABR_M16 (225, M16, BURST, ACOG, REL, "ABR_M16", 0.598),
    TBR_M16 (226, M16, BURST, TACT, REL, "TBR_M16", 0.598),
    ISR_M16 (227, M16, SEMI,  IRON, REL, "ISR_M16", 0.598),
    ASR_M16 (228, M16, SEMI,  ACOG, REL, "ASR_M16", 0.598),
    TSR_M16 (229, M16, SEMI,  TACT, REL, "TSR_M16", 0.598),
    IAR_M16 (230, M16, AUTO,  IRON, REL, "IAR_M16", 0.598),
    AAR_M16 (231, M16, AUTO,  ACOG, REL, "AAR_M16", 0.598),
    TAR_M16 (232, M16, AUTO,  TACT, REL, "TAR_M16", 0.598),
    IBE_M16 (233, M16, BURST, IRON, NA,  "IBE_M16", 0.598),
    ABE_M16 (234, M16, BURST, ACOG, NA,  "ABE_M16", 0.598),
    TBE_M16 (235, M16, BURST, TACT, NA,  "TBE_M16", 0.598),
    ISE_M16 (236, M16, SEMI,  IRON, NA,  "ISE_M16", 0.598),
    ASE_M16 (237, M16, SEMI,  ACOG, NA,  "ASE_M16", 0.598),
    TSE_M16 (238, M16, SEMI,  TACT, NA,  "TSE_M16", 0.598),
    IAE_M16 (239, M16, AUTO,  IRON, NA,  "IAE_M16", 0.598),
    AAE_M16 (240, M16, AUTO,  ACOG, NA,  "AAE_M16", 0.598),
    TAE_M16 (241, M16, AUTO,  TACT, NA,  "TAE_M16", 0.598),
    
    IA_GALIL (22,  GALIL, AUTO,   IRON,  NA, "GALIL",    0.85),
    AA_GALIL (254, GALIL, AUTO,   ACOG,  NA, "AA_GALIL", 0.85),
    TA_GALIL (255, GALIL, AUTO,   TACT,  NA, "TA_GALIL", 0.85),
    IB_GALIL (256, GALIL, BURST,  IRON,  NA, "IB_GALIL", 0.85),
    AB_GALIL (257, GALIL, BURST,  ACOG,  NA, "AB_GALIL", 0.85),
    TB_GALIL (258, GALIL, BURST,  TACT,  NA, "TB_GALIL", 0.85),
    IS_GALIL (259, GALIL, SEMI,   IRON,  NA, "IS_GALIL", 0.85),
    AS_GALIL (260, GALIL, SEMI,   ACOG,  NA, "AS_GALIL", 0.85),
    TS_GALIL (261, GALIL, SEMI,   TACT,  NA, "TS_GALIL", 0.85),
    IAE_GALIL (262, GALIL, AUTO,  IRON, EXT, "IAE_GALIL", 0.85),
    AAE_GALIL (263, GALIL, AUTO,  ACOG, EXT, "AAE_GALIL", 0.85),
    TAE_GALIL (264, GALIL, AUTO,  TACT, EXT, "TAE_GALIL", 0.85),
    IBE_GALIL (265, GALIL, BURST, IRON, EXT, "IBE_GALIL", 0.85),
    ABE_GALIL (266, GALIL, BURST, ACOG, EXT, "ABE_GALIL", 0.85),
    TBE_GALIL (267, GALIL, BURST, TACT, EXT, "TBE_GALIL", 0.85),
    ISE_GALIL (268, GALIL, SEMI,  IRON, EXT, "ISE_GALIL", 0.85),
    ASE_GALIL (269, GALIL, SEMI,  ACOG, EXT, "ASE_GALIL", 0.85),
    TSE_GALIL (270, GALIL, SEMI,  TACT, EXT, "TSE_GALIL", 0.85),
    IAR_GALIL (271, GALIL, AUTO,  IRON, REL, "IAR_GALIL", 0.85),
    AAR_GALIL (272, GALIL, AUTO,  ACOG, REL, "AAR_GALIL", 0.85),
    TAR_GALIL (273, GALIL, AUTO,  TACT, REL, "TAR_GALIL", 0.85),
    IBR_GALIL (274, GALIL, BURST, IRON, REL, "IBR_GALIL", 0.85),
    ABR_GALIL (275, GALIL, BURST, ACOG, REL, "ABR_GALIL", 0.85),
    TBR_GALIL (276, GALIL, BURST, TACT, REL, "TBR_GALIL", 0.85),
    ISR_GALIL (277, GALIL, SEMI,  IRON, REL, "ISR_GALIL", 0.85),
    ASR_GALIL (278, GALIL, SEMI,  ACOG, REL, "ASR_GALIL", 0.85),
    TSR_GALIL (279, GALIL, SEMI,  TACT, REL, "TSR_GALIL", 0.85),
    
    FAMAS1 (23, FAMAS, AUTO, IRON, NA, "FAMAS", 0.697),
    M4A11 (24, M4A1, AUTO, IRON, SUP, "M4A1", 0.704),
    SAW1 (25, SAW,  AUTO, IRON, NA, "SAW", 1.215),
    
    /* SHOTGUNS ***********************************************************************************************************************************/
    Olympia (26, OLYMPIA, SING,IRON, NA, "Olympia", 2.43),
    
    AA12a (27, AA12, SEMI,  IRON, NA, "AA12", 2.0),
    
    M3a (28, M3, PUMP, IRON, NA, "M3", 1.88),
    
    XM1014a (29, XM1014, SEMI, IRON, NA, "XM1014", 2.2);
    
    private static final Random rand = new Random();
    
    private final int uniqueID;
    private final CrackshotBase base;
    private final FireMode firemodeType;
    private final Scope scopeType;
    private final Attatchment attatchment;
    private final String csWeaponName;
    private final double initialBulletSpread;
    
    private CrackshotGun(final int uniqueID,
                final CrackshotBase base,
                final FireMode firemodeType,
                final Scope scopeType,
                final Attatchment attatchment,
                final String crackshotWeaponName,
                final double initialBulletSpread)
    {
        this.uniqueID = uniqueID;
        this.base = base;
        this.firemodeType = firemodeType;
        this.scopeType = scopeType;
        this.attatchment = attatchment;
        this.csWeaponName = crackshotWeaponName;
        this.initialBulletSpread = initialBulletSpread;
    }
    
    public CrackshotBase   getBase()         { return base;                    }
    public int         getUniqueId()         { return uniqueID;                }
    public Weapon      getWeaponType()       { return base.getWeaponType();    }
    public FireMode    getFireMode()         { return firemodeType;            }
    public Scope       getScopeType()        { return scopeType;               }
    public Attatchment getAttatchment()      { return attatchment;             }
    public int         getItemID()           { return base.getItemID();        }
    public int         getItemData()         { return base.getItemData();      }
    public String      getCSWeaponName()     { return csWeaponName;            }
    public double      getInitBulletSpread() { return initialBulletSpread;     }
    public int         getMaxDurability()    { return base.getMaxDurability(); }
    public ArrayList<Mod> getModifiedIDs()   { return gatherModifiedIDs();     }
    
    @Override public String toString()         { return csWeaponName;  }
    @Override public int getEnumValue()        { return uniqueID; }

    public double getCSBulletSpread()
    {
        switch(scopeType)
        {
        case IRON: return initialBulletSpread;
        case ACOG: return initialBulletSpread + 0.25;
        case TACT: return initialBulletSpread + 0.50;
        case LONG: return initialBulletSpread + 1.00;
        default:   return initialBulletSpread;
        }
    }
    
    public double getSneakCSBulletSpread()
    {
        return (GunUtils.hasScope(this)) ? getCSBulletSpread() : initialBulletSpread * 0.85;
    }
    
    public int getInitialDurability()
    {
        final int maxDurability = this.getMaxDurability();
        return maxDurability - rand.nextInt(maxDurability/2);
    }
    
    /**
     * Returns the integer of the gun condition.
     * @param durability Current durability of the gun.
     * @return Gun tier integer.
     */
    public int getConditionInt(final int durability)
    {
        final double ratio = (double)durability / (double)this.getMaxDurability();
        for (int i = 0; i <= Condition.TIERS; i++)
            if (Double.compare(ratio, (double)i/(double)Condition.TIERS) <= 0) return i;
        
        return Condition.TIERS;
    }

    public int getRepairPrice(final ItemStack item)
    {
        final int durability = CrackshotLore.getDurability(item);
        final double buildWeight = (double)CrackshotLore.getBuild(item)/10.0;
        
        if (durability < 0 || buildWeight < 0)
            return -1;
        
        return (int)((double)(this.getMaxDurability() - durability) 
                * this.base.getWeaponType().getRepairPriceWeight()
                / (1.0 - buildWeight));
    }
    
    public int getUpgradePrice(final ItemStack item)
    {
        final int build = CrackshotLore.getBuild(item);
        
        if (build < 0)
            return -1;
        else if (build == Build.ENHANCED.getEnumValue())
            return 0;
        else
            return (int)((build + 1) * this.getWeaponType().getUpgradePriceWeight());
    }
    
    public CrackshotGun getModifiedGun(final ModType modType)
    {
        for (Mod mod : this.getModifiedIDs())
            if (mod.getModType().equals(modType))
                return getGun(mod.getID());
        return null;
    }
    
    /**
     * Sorts list of possible modifications by price, descending.
     * @return List of modifications sorted by price.
     */
    public String[] getModifiedList()
    {
        ArrayList<Mod> modifiedIDs = this.getModifiedIDs();
        String toReturn[] = new String[modifiedIDs.size()];
        
        for (int i = 0; i < modifiedIDs.size(); i++)
            toReturn[i] = "$" + modifiedIDs.get(i).getPrice() + " - " + modifiedIDs.get(i).toString();
        
        return toReturn;
    }
    
    private ArrayList<Mod> gatherModifiedIDs()
    {
        final ArrayList<Mod> Mods = new ArrayList<>();
        Mod tempMod;
        
        for (CrackshotGun gun : CrackshotGun.values())
        {
            if (gun.base.equals(this.base))
            {
                tempMod = Mod.getMod(this, gun);
                if (tempMod != null)
                    Mods.add(tempMod);
            }
        }
        Collections.sort(Mods);
        return Mods;
    }
    
    static
    public CrackshotGun getGun(final int ID)
    {
        return GunAccess.get(ID);
    }
    
    static
    public CrackshotGun getGun(final ItemStack item)
    {
        return getGun(CrackshotLore.getWeaponID(item));
    }
}
