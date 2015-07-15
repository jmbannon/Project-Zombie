/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.FireMode.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Scope.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Attatchment.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunAccess;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jbannon
 */
public enum Gun implements Type
{
    //SWITCH AUG AND SG TO ASSULT; SWITCH G3 TO AUTO SNIP
    
// A-ACOG, B-Burst, S-Suppressed
    
    
//  Name        GunID, Type, Fire, Sight, Supp, Id,Data, CSname,  BSpread, MaxDur, Modifications: Scope,  Fire, Suppressor
    
    /* PISTOLS ************************************************************************************************************************************/
    I_Deagle    (0,  PISTOL, SEMI,  IRON, NA,  93,  0, "Deagle",   1.101, 120, new Mod[] {new Mod(30, ACOG_)}),
    A_Deagle    (30, PISTOL, SEMI,  ACOG, NA,  293, 0, "A_Deagle", 1.101, 120, new Mod[] {new Mod(0,  IRON_)}),
    
    I_Colt45    (1,  PISTOL, SEMI,  IRON, NA,  338, 0, "Colt45",   1.40,  120, new Mod[] {new Mod(31, ACOG_)}),
    A_Colt45    (31, PISTOL, SEMI,  ACOG, NA,  338, 0, "A_Colt45", 1.40,  120, new Mod[] {new Mod(1,  IRON_)}),
    
    I___P228    (2,  PISTOL, SEMI,  IRON, NA, 351, 8, "P228",     1.09,  120, new Mod[] {new Mod(32, ACOG_), new Mod(33, BURST_), new Mod(35, SUPP_)}),
    A___P228    (32, PISTOL, SEMI,  ACOG, NA, 351, 8, "A_P228",   1.09,  120, new Mod[] {new Mod(2,  IRON_), new Mod(34, BURST_), new Mod(36, SUPP_)}),
    IB__P228    (33, PISTOL, BURST, IRON, NA, 351, 8, "B_P228",   1.09,  120, new Mod[] {new Mod(34, ACOG_), new Mod(2,  SEMI_),  new Mod(37, SUPP_)}),
    AB__P228    (34, PISTOL, BURST, ACOG, NA, 351, 8, "AB_P228",  1.09,  120, new Mod[] {new Mod(33, IRON_), new Mod(32, SEMI_),  new Mod(38, SUPP_)}),
    I_S_P228    (35, PISTOL, SEMI,  IRON, SUP,  351, 8, "S_P228",   1.19,  120, new Mod[] {new Mod(32, ACOG_), new Mod(33, BURST_), new Mod(2,  NA_)}),
    A_S_P228    (36, PISTOL, SEMI,  ACOG, SUP,  351, 8, "SA_P228",  1.19,  120, new Mod[] {new Mod(2,  IRON_), new Mod(34, BURST_), new Mod(32, NA_)}),
    IBS_P228    (37, PISTOL, BURST, IRON, SUP,  351, 8, "SB_P228",  1.19,  120, new Mod[] {new Mod(34, ACOG_), new Mod(2,  SEMI_),  new Mod(33, NA_)}),
    ABS_P228    (38, PISTOL, BURST, ACOG, SUP,  351, 8, "SAB_P228", 1.19,  120, new Mod[] {new Mod(33, IRON_), new Mod(32, SEMI_),  new Mod(34, NA_)}),
    
    I__USP      (3,  PISTOL, SEMI,  IRON, SUP,  292, 0, "USP",      1.32,  120, new Mod[] {new Mod(39, ACOG_), new Mod(40, BURST_)}),
    A__USP      (39, PISTOL, SEMI,  ACOG, SUP,  292, 0, "A_USP",    1.32,  120, new Mod[] {new Mod(3,  IRON_), new Mod(41, BURST_)}),
    IB_USP      (40, PISTOL, BURST, IRON, SUP,  292, 0, "B_USP",    1.32,  120, new Mod[] {new Mod(41, ACOG_), new Mod(3,  SEMI_)}),
    AB_USP      (41, PISTOL, BURST, ACOG, SUP,  292, 0, "AB_USP",   1.32,  120, new Mod[] {new Mod(40, IRON_), new Mod(39, SEMI_)}),
    
    /* REVOLVERS **********************************************************************************************************************************/
    Mag44       (4, REVOLVER, SEMI, IRON, NA, 294, 0, "44Mag",      0.78, 120, new Mod[]{}),
    DirtyFrank  (5, REVOLVER, SEMI, ACOG, NA, 336, 0, "DirtyFrank", 0.50, 120, new Mod[]{}),
    
    /* SNIPERS ************************************************************************************************************************************/
    L__Scout     (6,  SNIPER, BOLT, LONG, NA, 351, 9, "Scout",    0.28, 120, new Mod[] {new Mod(42, TACT_), new Mod(43, ACOG_), new Mod(44, IRON_), new Mod(45, SUPP_)}),
    T__Scout     (42, SNIPER, BOLT, TACT, NA, 351, 9, "T_Scout",  0.33, 120, new Mod[] {new Mod(6, LONG_),  new Mod(43, ACOG_), new Mod(44, IRON_), new Mod(46, SUPP_)}),
    A__Scout     (43, SNIPER, BOLT, ACOG, NA, 351, 9, "A_Scout",  0.38, 120, new Mod[] {new Mod(6, LONG_),  new Mod(42, TACT_), new Mod(44, IRON_), new Mod(47, SUPP_)}),
    I__Scout     (44, SNIPER, BOLT, IRON, NA, 351, 9, "I_Scout",  0.43, 120, new Mod[] {new Mod(6, LONG_),  new Mod(42, TACT_), new Mod(43, ACOG_), new Mod(48, SUPP_)}),
    LS_Scout     (45, SNIPER, BOLT, LONG, SUP,  351, 9, "LS_Scout", 0.33, 120, new Mod[] {new Mod(46, TACT_), new Mod(47, ACOG_), new Mod(48, IRON_), new Mod(6, NA_)}),
    TS_Scout     (46, SNIPER, BOLT, TACT, SUP,  351, 9, "TS_Scout", 0.38, 120, new Mod[] {new Mod(45, LONG_), new Mod(47, ACOG_), new Mod(48, IRON_), new Mod(42, NA_)}),
    AS_Scout     (47, SNIPER, BOLT, ACOG, SUP,  351, 9, "AS_Scout", 0.43, 120, new Mod[] {new Mod(45, LONG_), new Mod(46, TACT_), new Mod(48, IRON_), new Mod(43, NA_)}),
    IS_Scout     (48, SNIPER, BOLT, IRON, SUP,  351, 9, "IS_Scout", 0.48, 120, new Mod[] {new Mod(45, LONG_), new Mod(46, TACT_), new Mod(47, ACOG_), new Mod(44, NA_)}),
    
    //TODO
    L_AWP         (8,  SNIPER, BOLT, LONG, NA,  278, 0,  "AWP",   0.16, 120, new Mod[] {new Mod(49, TACT_), new Mod(50, ACOG_), new Mod(51, IRON_)}),
    T_AWP         (49, SNIPER, BOLT, TACT, NA,  278, 0,  "T_AWP", 0.21, 120, new Mod[] {new Mod(8, LONG_),  new Mod(50, ACOG_), new Mod(51, IRON_)}),
    A_AWP         (50, SNIPER, BOLT, ACOG, NA,  278, 0,  "A_AWP", 0.26, 120, new Mod[] {new Mod(8, LONG_),  new Mod(49, TACT_), new Mod(51, IRON_)}),
    I_AWP         (51, SNIPER, BOLT, IRON, NA,  278, 0,  "I_AWP", 0.31, 120, new Mod[] {new Mod(8, LONG_),  new Mod(59, TACT_), new Mod(50, ACOG_)}),
    
    /* AUTO-SNIPERS *******************************************************************************************************************************/
    LS_G3SG1       (19, AUTO_S, SEMI, LONG, SUP,  277, 0,  "G3SG1",    0.48, 120, new Mod[] {new Mod(52, TACT_), new Mod(53, ACOG_), new Mod(54, IRON_), new Mod(55, NA_)}),
    TS_G3SG1       (52, AUTO_S, SEMI, TACT, SUP,  277, 0,  "TS_G3SG1", 0.48, 120, new Mod[] {new Mod(19, LONG_), new Mod(53, ACOG_), new Mod(54, IRON_), new Mod(56, NA_)}),
    AS_G3SG1       (53, AUTO_S, SEMI, ACOG, SUP,  277, 0,  "AS_G3SG1", 0.48, 120, new Mod[] {new Mod(19, LONG_), new Mod(52, TACT_), new Mod(54, IRON_), new Mod(57, NA_)}),
    IS_G3SG1       (54, AUTO_S, SEMI, IRON, SUP,  277, 0,  "IS_G3SG1", 0.48, 120, new Mod[] {new Mod(19, LONG_), new Mod(52, TACT_), new Mod(53, ACOG_), new Mod(58, NA_)}),   
    L__G3SG1       (55, AUTO_S, SEMI, LONG, NA, 277, 0,  "L_G3SG1",  0.48, 120, new Mod[] {new Mod(56, TACT_), new Mod(57, ACOG_), new Mod(58, IRON_), new Mod(19, SUPP_)}),
    T__G3SG1       (56, AUTO_S, SEMI, TACT, NA, 277, 0,  "T_G3SG1",  0.48, 120, new Mod[] {new Mod(55, LONG_), new Mod(57, ACOG_), new Mod(58, IRON_), new Mod(52, SUPP_)}),
    A__G3SG1       (57, AUTO_S, SEMI, ACOG, NA, 277, 0,  "A_G3SG1",  0.48, 120, new Mod[] {new Mod(55, LONG_), new Mod(56, TACT_), new Mod(58, IRON_), new Mod(53, SUPP_)}),
    I__G3SG1       (58, AUTO_S, SEMI, IRON, NA, 277, 0,  "I_G3SG1",  0.48, 120, new Mod[] {new Mod(55, LONG_), new Mod(56, TACT_), new Mod(57, ACOG_), new Mod(54, SUPP_)}),
    
    L_Dragonuv    (7,  AUTO_S, SEMI, LONG, NA, 351, 14, "Dragonuv",   0.46, 120, new Mod[] {new Mod(59, TACT_), new Mod(60, ACOG_), new Mod(61, IRON_)}),
    T_Dragonuv    (59, AUTO_S, SEMI, TACT, NA, 351, 14, "T_Dragonuv", 0.46, 120, new Mod[] {new Mod(7, LONG_),  new Mod(60, ACOG_), new Mod(61, IRON_)}),
    A_Dragonuv    (60, AUTO_S, SEMI, ACOG, NA, 351, 14, "A_Dragonuv", 0.46, 120, new Mod[] {new Mod(7, LONG_),  new Mod(59, TACT_), new Mod(61, IRON_)}),
    I_Dragonuv    (61, AUTO_S, SEMI, IRON, NA, 351, 14, "I_Dragonuv", 0.46, 120, new Mod[] {new Mod(7, LONG_),  new Mod(59, TACT_), new Mod(60, ACOG_)}),
    
    /* HUNTING RIFLES *****************************************************************************************************************************/
    L_Remington   (9,  HUNTING, BOLT, LONG, NA, 351, 12, "Remington700",   0.26, 120, new Mod[] {new Mod(62, TACT_), new Mod(63, ACOG_), new Mod(64, IRON_)}),
    T_Remington   (62, HUNTING, BOLT, TACT, NA, 351, 12, "T_Remington700", 0.26, 120, new Mod[] {new Mod(9, LONG_),  new Mod(63, ACOG_), new Mod(64, IRON_)}),
    A_Remington   (63, HUNTING, BOLT, ACOG, NA, 351, 12, "A_Remington700", 0.26, 120, new Mod[] {new Mod(9, LONG_),  new Mod(62, TACT_), new Mod(64, IRON_)}),
    I_Remington   (64, HUNTING, BOLT, IRON, NA, 351, 12, "I_Remington700", 0.26, 120, new Mod[] {new Mod(9, LONG_),  new Mod(62, TACT_), new Mod(63, ACOG_)}),
    
    I_Springfield (10, HUNTING, BOLT, IRON, NA, 351, 13, "M1903",   0.52, 120, new Mod[] {new Mod(65, ACOG_), new Mod(66, TACT_), new Mod(67, LONG_)}),
    A_Springfield (65, HUNTING, BOLT, ACOG, NA, 351, 13, "A_M1903", 0.52, 120, new Mod[] {new Mod(10, IRON_), new Mod(66, TACT_), new Mod(67, LONG_)}),
    T_Springfield (66, HUNTING, BOLT, TACT, NA, 351, 13, "T_M1903", 0.52, 120, new Mod[] {new Mod(10, IRON_), new Mod(65, ACOG_), new Mod(67, LONG_)}),
    L_Springfield (67, HUNTING, BOLT, LONG, NA, 351, 13, "L_M1903", 0.52, 120, new Mod[] {new Mod(10, IRON_), new Mod(65, ACOG_), new Mod(66, TACT_)}),
    
    L_Model70 (11,  HUNTING, BOLT, LONG, NA, 351, 9, "Model70",   0.41, 120, new Mod[] {new Mod(68, TACT_), new Mod(69, ACOG_), new Mod(70, IRON_)}),
    T_Model70 (68,  HUNTING, BOLT, TACT, NA, 351, 9, "T_Model70", 0.41, 120, new Mod[] {new Mod(11, LONG_), new Mod(69, ACOG_), new Mod(70, IRON_)}),
    A_Model70 (69,  HUNTING, BOLT, ACOG, NA, 351, 9, "A_Model70", 0.41, 120, new Mod[] {new Mod(11, LONG_), new Mod(68, TACT_), new Mod(70, IRON_)}),
    I_Model70 (70,  HUNTING, BOLT, IRON, NA, 351, 9, "I_Model70", 0.41, 120, new Mod[] {new Mod(11, LONG_), new Mod(68, TACT_), new Mod(69, ACOG_)}),
    
    /* SMGS ***************************************************************************************************************************************/
    IA__Mac10 (12, SMG, AUTO,  IRON, NA, 351, 6, "Mac10",        1.25, 120, new Mod[] {new Mod(71, ACOG_), new Mod(72, BURST_), new Mod(74, SEMI_), new Mod(76, SUPP_)}),
    AA__Mac10 (71, SMG, AUTO,  ACOG, NA, 351, 6, "AA_Mac10",     1.25, 120, new Mod[] {new Mod(12, IRON_), new Mod(73, BURST_), new Mod(75, SEMI_), new Mod(77, SUPP_)}), 
    IB__Mac10 (72, SMG, BURST, IRON, NA, 351, 6, "IB_Mac10",     1.25, 120, new Mod[] {new Mod(12, AUTO_), new Mod(73, ACOG_),  new Mod(74, SEMI_), new Mod(78, SUPP_)}),
    AB__Mac10 (73, SMG, BURST, ACOG, NA, 351, 6, "AB_Mac10",     1.25, 120, new Mod[] {new Mod(71, AUTO_), new Mod(72, IRON_),  new Mod(75, SEMI_), new Mod(79, SUPP_)}),
    IS__Mac10 (74, SMG, SEMI,  IRON, NA, 351, 6, "IS_Mac10",     1.25, 120, new Mod[] {new Mod(12, AUTO_), new Mod(72, BURST_), new Mod(75, ACOG_), new Mod(80, SUPP_)}),
    AS__Mac10 (75, SMG, SEMI,  ACOG, NA, 351, 6, "AS_Mac10",     1.25, 120, new Mod[] {new Mod(71, AUTO_), new Mod(73, BURST_), new Mod(74, IRON_), new Mod(81, SUPP_)}),
    IAS_Mac10 (76, SMG, AUTO,  IRON, SUP,  351, 6, "IAS_Mac10", 1.25, 120, new Mod[] {new Mod(77, ACOG_), new Mod(78, BURST_), new Mod(80, SEMI_), new Mod(12, NA_)}),
    AAS_Mac10 (77, SMG, AUTO,  ACOG, SUP,  351, 6, "AAS_Mac10", 1.25, 120, new Mod[] {new Mod(76, IRON_), new Mod(79, BURST_), new Mod(81, SEMI_), new Mod(71, NA_)}),
    IBS_Mac10 (78, SMG, BURST, IRON, SUP,  351, 6, "IBS_Mac10", 1.25, 120, new Mod[] {new Mod(76, AUTO_), new Mod(79, ACOG_),  new Mod(80, SEMI_), new Mod(72, NA_)}),
    ABS_Mac10 (79, SMG, BURST, ACOG, SUP,  351, 6, "ABS_Mac10", 1.25, 120, new Mod[] {new Mod(77, AUTO_), new Mod(78, IRON_),  new Mod(81, SEMI_), new Mod(73, NA_)}),
    ISS_Mac10 (80, SMG, SEMI,  IRON, SUP,  351, 6, "ISS_Mac10", 1.25, 120, new Mod[] {new Mod(76, AUTO_), new Mod(78, BURST_), new Mod(81, ACOG_), new Mod(74, NA_)}),
    ASS_Mac10 (81, SMG, SEMI,  ACOG, SUP,  351, 6, "ASS_Mac10", 1.25, 120, new Mod[] {new Mod(77, AUTO_), new Mod(79, BURST_), new Mod(80, IRON_), new Mod(75, NA_)}),
    
    IA__Ump45 (13, SMG, AUTO,  IRON, NA, 351, 10, "Ump45",     0.99, 120, new Mod[] {new Mod(82, ACOG_), new Mod(83, BURST_), new Mod(85, SEMI_), new Mod(87, SUPP_)}),
    AA__Ump45 (82, SMG, AUTO,  ACOG, NA, 351, 10, "AA_Ump45",  0.99, 120, new Mod[] {new Mod(13, IRON_), new Mod(84, BURST_), new Mod(86, SEMI_), new Mod(88, SUPP_)}),
    IB__Ump45 (83, SMG, BURST, IRON, NA, 351, 10, "IB_Ump45",  0.99, 120, new Mod[] {new Mod(13, AUTO_), new Mod(84, ACOG_),  new Mod(85, SEMI_), new Mod(89, SUPP_)}),
    AB__Ump45 (84, SMG, BURST, ACOG, NA, 351, 10, "AB_Ump45",  0.99, 120, new Mod[] {new Mod(82, AUTO_), new Mod(83, IRON_),  new Mod(86, SEMI_), new Mod(90, SUPP_)}),
    IS__Ump45 (85, SMG, SEMI,  IRON, NA, 351, 10, "IS_Ump45",  0.99, 120, new Mod[] {new Mod(13, AUTO_), new Mod(83, BURST_), new Mod(86, ACOG_), new Mod(91, SUPP_)}),
    AS__Ump45 (86, SMG, SEMI,  ACOG, NA, 351, 10, "AS_Ump45",  0.99, 120, new Mod[] {new Mod(82, AUTO_), new Mod(84, BURST_), new Mod(85, IRON_), new Mod(82, SUPP_)}),
    IAS_Ump45 (87, SMG, AUTO,  IRON, NA, 351, 10, "IAS_Ump45", 0.99, 120, new Mod[] {new Mod(88, ACOG_), new Mod(89, BURST_), new Mod(91, SEMI_), new Mod(13, NA_)}),
    AAS_Ump45 (88, SMG, AUTO,  ACOG, NA, 351, 10, "AAS_Ump45", 0.99, 120, new Mod[] {new Mod(87, IRON_), new Mod(90, BURST_), new Mod(92, SEMI_), new Mod(82, NA_)}),
    IBS_Ump45 (89, SMG, BURST, IRON, NA, 351, 10, "IBS_Ump45", 0.99, 120, new Mod[] {new Mod(87, AUTO_), new Mod(90, ACOG_),  new Mod(91, SEMI_), new Mod(83, NA_)}),
    ABS_Ump45 (90, SMG, BURST, ACOG, NA, 351, 10, "ABS_Ump45", 0.99, 120, new Mod[] {new Mod(88, AUTO_), new Mod(89, IRON_),  new Mod(92, SEMI_), new Mod(84, NA_)}),
    ISS_Ump45 (91, SMG, SEMI,  IRON, NA, 351, 10, "ISS_Ump45", 0.99, 120, new Mod[] {new Mod(87, AUTO_), new Mod(89, BURST_), new Mod(92, ACOG_), new Mod(85, NA_)}),
    ASS_Ump45 (92, SMG, SEMI,  ACOG, NA, 351, 10, "ASS_Ump45", 0.99, 120, new Mod[] {new Mod(88, AUTO_), new Mod(90, BURST_), new Mod(91, IRON_), new Mod(86, NA_)}),
    
    IA_P90 (14, SMG, AUTO,  IRON, NA, 351, 7, "P90",    1.16, 120, new Mod[] {new Mod(93, ACOG_), new Mod(94, BURST_), new Mod(96, SEMI_)}),
    AA_P90 (93, SMG, AUTO,  ACOG, NA, 351, 7, "AA_P90", 1.16, 120, new Mod[] {new Mod(14, IRON_), new Mod(95, BURST_), new Mod(97, SEMI_)}),
    IB_P90 (94, SMG, BURST, IRON, NA, 351, 7, "IB_P90", 1.16, 120, new Mod[] {new Mod(14, AUTO_), new Mod(95, ACOG_),  new Mod(96, SEMI_)}),
    AB_P90 (95, SMG, BURST, ACOG, NA, 351, 7, "AB_P90", 1.16, 120, new Mod[] {new Mod(93, AUTO_), new Mod(94, IRON_),  new Mod(97, SEMI_)}),
    IS_P90 (96, SMG, SEMI,  IRON, NA, 351, 7, "IS_P90", 1.16, 120, new Mod[] {new Mod(14, AUTO_), new Mod(94, BURST_), new Mod(97, ACOG_)}),
    AS_P90 (97, SMG, SEMI,  ACOG, NA, 351, 7, "AS_P90", 1.16, 120, new Mod[] {new Mod(93, AUTO_), new Mod(95, BURST_), new Mod(96, IRON_)}),
    
    IS__MP5 (15,  SMG, SEMI,  IRON, NA,    285, 0, "MP5",     0.93, 120, new Mod[] {new Mod(98, ACOG_),  new Mod(99, TACT_),  new Mod(100, BURST_), new Mod(103, AUTO_),  new Mod(106, SUPP_)}),
    AS__MP5 (98,  SMG, SEMI,  ACOG, NA,    285, 0, "AS_MP5",  0.93, 120, new Mod[] {new Mod(15, IRON_),  new Mod(99, TACT_),  new Mod(101, BURST_), new Mod(104, AUTO_),  new Mod(107, SUPP_)}),
    TS__MP5 (99,  SMG, SEMI,  TACT, NA,    285, 0, "TS_MP5",  0.93, 120, new Mod[] {new Mod(15, IRON_),  new Mod(98, ACOG_),  new Mod(102, BURST_), new Mod(105, AUTO_),  new Mod(108, SUPP_)}),
    IB__MP5 (100, SMG, BURST, IRON, NA,    285, 0, "IB_MP5",  0.93, 120, new Mod[] {new Mod(101, ACOG_), new Mod(102, TACT_), new Mod(15, SEMI_),   new Mod(103, AUTO_),  new Mod(109, SUPP_)}),
    AB__MP5 (101, SMG, BURST, ACOG, NA,    285, 0, "AB_MP5",  0.93, 120, new Mod[] {new Mod(100, IRON_), new Mod(102, TACT_), new Mod(98, SEMI_),   new Mod(104, AUTO_),  new Mod(110, SUPP_)}),
    TB__MP5 (102, SMG, BURST, TACT, NA,    285, 0, "TB_MP5",  0.93, 120, new Mod[] {new Mod(100, IRON_), new Mod(101, ACOG_), new Mod(99, SEMI_),   new Mod(105, AUTO_),  new Mod(111, SUPP_)}),
    IA__MP5 (103, SMG, AUTO,  IRON, NA,    285, 0, "TA_MP5",  0.93, 120, new Mod[] {new Mod(104, ACOG_), new Mod(105, TACT_), new Mod(15, SEMI_),   new Mod(100, BURST_), new Mod(112, SUPP_)}),
    AA__MP5 (104, SMG, AUTO,  ACOG, NA,    285, 0, "AA_MP5",  0.93, 120, new Mod[] {new Mod(103, IRON_), new Mod(105, TACT_), new Mod(98, SEMI_),   new Mod(101, BURST_), new Mod(113, SUPP_)}),
    TA__MP5 (105, SMG, AUTO,  TACT, NA,    285, 0, "TA_MP5",  0.93, 120, new Mod[] {new Mod(103, IRON_), new Mod(104, ACOG_), new Mod(99, SEMI_),   new Mod(102, BURST_), new Mod(114, SUPP_)}),
    ISS_MP5 (106, SMG, SEMI,  IRON, SUP,  285, 0, "ISS_MP5", 0.93, 120, new Mod[] {new Mod(107, ACOG_), new Mod(108, TACT_), new Mod(109, BURST_), new Mod(112, AUTO_),  new Mod(15, NA_)}),
    ASS_MP5 (107, SMG, SEMI,  ACOG, SUP,  285, 0, "ASS_MP5", 0.93, 120, new Mod[] {new Mod(106, IRON_), new Mod(108, TACT_), new Mod(110, BURST_), new Mod(113, AUTO_),  new Mod(98, NA_)}),
    TSS_MP5 (108, SMG, SEMI,  TACT, SUP,  285, 0, "TSS_MP5", 0.93, 120, new Mod[] {new Mod(106, IRON_), new Mod(107, ACOG_), new Mod(111, BURST_), new Mod(114, AUTO_),  new Mod(99, NA_)}),
    IBS_MP5 (109, SMG, BURST, IRON, SUP,  285, 0, "IBS_MP5", 0.93, 120, new Mod[] {new Mod(110, ACOG_), new Mod(111, TACT_), new Mod(106, SEMI_),  new Mod(112, AUTO_),  new Mod(100, NA_)}),
    ABS_MP5 (110, SMG, BURST, ACOG, SUP,  285, 0, "ABS_MP5", 0.93, 120, new Mod[] {new Mod(109, IRON_), new Mod(111, TACT_), new Mod(107, SEMI_),  new Mod(113, AUTO_),  new Mod(101, NA_)}),
    TBS_MP5 (111, SMG, BURST, TACT, SUP,  285, 0, "TBS_MP5", 0.93, 120, new Mod[] {new Mod(109, IRON_), new Mod(110, ACOG_), new Mod(108, SEMI_),  new Mod(114, AUTO_),  new Mod(102, NA_)}),
    IAS_MP5 (112, SMG, AUTO,  IRON, SUP,  285, 0, "IAS_MP5", 0.93, 120, new Mod[] {new Mod(113, ACOG_), new Mod(114, TACT_), new Mod(106, SEMI_),  new Mod(109, BURST_), new Mod(103, NA_)}),
    AAS_MP5 (113, SMG, AUTO,  ACOG, SUP,  285, 0, "AAS_MP5", 0.93, 120, new Mod[] {new Mod(112, IRON_), new Mod(114, TACT_), new Mod(107, SEMI_),  new Mod(110, BURST_), new Mod(104, NA_)}),
    TAS_MP5 (114, SMG, AUTO,  TACT, SUP,  285, 0, "TAS_MP5", 0.93, 120, new Mod[] {new Mod(112, IRON_), new Mod(113, ACOG_), new Mod(108, SEMI_),  new Mod(111, BURST_), new Mod(105, NA_)}),
    
    IA_TMP (16,  SMG, AUTO,  IRON, SUP, 284, 0, "TMP",    1.05, 120, new Mod[] {new Mod(115, ACOG_), new Mod(116, BURST_), new Mod(118, SEMI_)}),
    AA_TMP (115, SMG, AUTO,  ACOG, SUP, 284, 0, "AA_TMP", 1.05, 120, new Mod[] {new Mod(16, IRON_),  new Mod(117, BURST_), new Mod(119, SEMI_)}),
    IB_TMP (116, SMG, BURST, IRON, SUP, 284, 0, "IB_TMP", 1.05, 120, new Mod[] {new Mod(117, ACOG_), new Mod(16,  AUTO_),  new Mod(118, SEMI_)}),
    AB_TMP (117, SMG, BURST, ACOG, SUP, 284, 0, "AB_TMP", 1.05, 120, new Mod[] {new Mod(116, IRON_), new Mod(115, AUTO_),  new Mod(119, SEMI_)}),
    IS_TMP (118, SMG, SEMI,  IRON, SUP, 284, 0, "IS_TMP", 1.05, 120, new Mod[] {new Mod(119, ACOG_), new Mod(16,  AUTO_),  new Mod(116, BURST_)}),
    AS_TMP (119, SMG, SEMI,  ACOG, SUP, 284, 0, "AS_TMP", 1.05, 120, new Mod[] {new Mod(118, IRON_), new Mod(115, AUTO_),  new Mod(117, BURST_)}),
    
    /* ASSAULT RIFLES *****************************************************************************************************************************/
    AA_SG552       (17,  ASSAULT, AUTO,  ACOG, NA,  351, 11, "SG552",     0.61, 120, new Mod[]{}),
    IA_SG552       (120, ASSAULT, AUTO,  IRON, NA,  351, 11, "IA_SG552",  0.61, 120, new Mod[]{}),
    TA_SG552       (121, ASSAULT, AUTO,  TACT, NA,  351, 11, "TA_SG552",  0.61, 120, new Mod[]{}),
    AB_SG552       (122, ASSAULT, BURST, ACOG, NA,  351, 11, "AB_SG552",  0.61, 120, new Mod[]{}),
    IB_SG552       (123, ASSAULT, BURST, IRON, NA,  351, 11, "IB_SG552",  0.61, 120, new Mod[]{}),
    TB_SG552       (124, ASSAULT, BURST, TACT, NA,  351, 11, "TB_SG552",  0.61, 120, new Mod[]{}),
    AS_SG552       (125, ASSAULT, SEMI,  ACOG, NA,  351, 11, "AS_SG552",  0.61, 120, new Mod[]{}),
    IS_SG552       (126, ASSAULT, SEMI,  IRON, NA,  351, 11, "IS_SG552",  0.61, 120, new Mod[]{}),
    TS_SG552       (127, ASSAULT, SEMI,  TACT, NA,  351, 11, "TS_SG552",  0.61, 120, new Mod[]{}),
    AAB_SG552      (128, ASSAULT, AUTO,  ACOG, EXT, 351, 11, "AAB_SG552", 0.61, 120, new Mod[]{}),
    IAB_SG552      (129, ASSAULT, AUTO,  IRON, EXT, 351, 11, "IAB_SG552", 0.61, 120, new Mod[]{}),
    TAB_SG552      (130, ASSAULT, AUTO,  TACT, EXT, 351, 11, "TAB_SG552", 0.61, 120, new Mod[]{}),
    ABB_SG552      (131, ASSAULT, BURST, ACOG, EXT, 351, 11, "ABB_SG552", 0.61, 120, new Mod[]{}),
    IBB_SG552      (132, ASSAULT, BURST, IRON, EXT, 351, 11, "IBB_SG552", 0.61, 120, new Mod[]{}),
    TBB_SG552      (133, ASSAULT, BURST, TACT, EXT, 351, 11, "TBB_SG552", 0.61, 120, new Mod[]{}),
    ASB_SG552      (134, ASSAULT, SEMI,  ACOG, EXT, 351, 11, "ASB_SG552", 0.61, 120, new Mod[]{}),
    ISB_SG552      (135, ASSAULT, SEMI,  IRON, EXT, 351, 11, "ISB_SG552", 0.61, 120, new Mod[]{}),
    TSB_SG552      (136, ASSAULT, SEMI,  TACT, EXT, 351, 11, "TSB_SG552", 0.61, 120, new Mod[]{}),
    AAR_SG552      (137, ASSAULT, AUTO,  ACOG, REL, 351, 11, "AAR_SG552", 0.61, 120, new Mod[]{}),
    IAR_SG552      (138, ASSAULT, AUTO,  IRON, REL, 351, 11, "IAR_SG552", 0.61, 120, new Mod[]{}),
    TAR_SG552      (139, ASSAULT, AUTO,  TACT, REL, 351, 11, "TAR_SG552", 0.61, 120, new Mod[]{}),
    ABR_SG552      (140, ASSAULT, BURST, ACOG, REL, 351, 11, "ABR_SG552", 0.61, 120, new Mod[]{}),
    IBR_SG552      (141, ASSAULT, BURST, IRON, REL, 351, 11, "IBR_SG552", 0.61, 120, new Mod[]{}),
    TBR_SG552      (142, ASSAULT, BURST, TACT, REL, 351, 11, "TBR_SG552", 0.61, 120, new Mod[]{}),
    ASR_SG552      (143, ASSAULT, SEMI,  ACOG, REL, 351, 11, "ASR_SG552", 0.61, 120, new Mod[]{}),
    ISR_SG552      (144, ASSAULT, SEMI,  IRON, REL, 351, 11, "ISR_SG552", 0.61, 120, new Mod[]{}),
    TSR_SG552      (145, ASSAULT, SEMI,  TACT, REL, 351, 11, "TSR_SG552", 0.61, 120, new Mod[]{}),
    
    AA_AUG         (18,  ASSAULT, AUTO,  ACOG, NA,  348, 0, "AUG",     0.55, 120, new Mod[]{}),
    IA_AUG         (146, ASSAULT, AUTO,  IRON, NA,  348, 0, "IA_AUG",  0.55, 120, new Mod[]{}),
    TA_AUG         (147, ASSAULT, AUTO,  TACT, NA,  348, 0, "TA_AUG",  0.55, 120, new Mod[]{}),
    LA_AUG         (148, ASSAULT, AUTO,  LONG, NA,  348, 0, "LA_AUG",  0.55, 120, new Mod[]{}),
    AB_AUG         (149, ASSAULT, BURST, ACOG, NA,  348, 0, "AB_AUG",  0.55, 120, new Mod[]{}),
    IB_AUG         (150, ASSAULT, BURST, IRON, NA,  348, 0, "IB_AUG",  0.55, 120, new Mod[]{}),
    TB_AUG         (151, ASSAULT, BURST, TACT, NA,  348, 0, "TB_AUG",  0.55, 120, new Mod[]{}),
    LB_AUG         (152, ASSAULT, BURST, LONG, NA,  348, 0, "LB_AUG",  0.55, 120, new Mod[]{}),
    AS_AUG         (153, ASSAULT, SEMI,  ACOG, NA,  348, 0, "AS_AUG",  0.55, 120, new Mod[]{}),
    IS_AUG         (154, ASSAULT, SEMI,  IRON, NA,  348, 0, "IS_AUG",  0.55, 120, new Mod[]{}),
    TS_AUG         (155, ASSAULT, SEMI,  TACT, NA,  348, 0, "TS_AUG",  0.55, 120, new Mod[]{}),
    LS_AUG         (156, ASSAULT, SEMI,  LONG, NA,  348, 0, "LS_AUG",  0.55, 120, new Mod[]{}),
    AAB_AUG        (157, ASSAULT, AUTO,  ACOG, EXT, 348, 0, "AAB_AUG", 0.55, 120, new Mod[]{}),
    IAB_AUG        (158, ASSAULT, AUTO,  IRON, EXT, 348, 0, "IAB_AUG", 0.55, 120, new Mod[]{}),
    TAB_AUG        (159, ASSAULT, AUTO,  TACT, EXT, 348, 0, "TAB_AUG", 0.55, 120, new Mod[]{}),
    LAB_AUG        (160, ASSAULT, AUTO,  LONG, EXT, 348, 0, "LAB_AUG", 0.55, 120, new Mod[]{}),
    ABB_AUG        (161, ASSAULT, BURST, ACOG, EXT, 348, 0, "ABB_AUG", 0.55, 120, new Mod[]{}),
    IBB_AUG        (162, ASSAULT, BURST, IRON, EXT, 348, 0, "IBB_AUG", 0.55, 120, new Mod[]{}),
    TBB_AUG        (163, ASSAULT, BURST, TACT, EXT, 348, 0, "TBB_AUG", 0.55, 120, new Mod[]{}),
    LBB_AUG        (164, ASSAULT, BURST, LONG, EXT, 348, 0, "LBB_AUG", 0.55, 120, new Mod[]{}),
    ASB_AUG        (165, ASSAULT, SEMI,  ACOG, EXT, 348, 0, "ASB_AUG", 0.55, 120, new Mod[]{}),
    ISB_AUG        (166, ASSAULT, SEMI,  IRON, EXT, 348, 0, "ISB_AUG", 0.55, 120, new Mod[]{}),
    TSB_AUG        (167, ASSAULT, SEMI,  TACT, EXT, 348, 0, "TSB_AUG", 0.55, 120, new Mod[]{}),
    LSB_AUG        (168, ASSAULT, SEMI,  LONG, EXT, 348, 0, "LSB_AUG", 0.55, 120, new Mod[]{}),
    AAR_AUG        (169, ASSAULT, AUTO,  ACOG, REL, 348, 0, "AAR_AUG", 0.55, 120, new Mod[]{}),
    IAR_AUG        (170, ASSAULT, AUTO,  IRON, REL, 348, 0, "IAR_AUG", 0.55, 120, new Mod[]{}),
    TAR_AUG        (171, ASSAULT, AUTO,  TACT, REL, 348, 0, "TAR_AUG", 0.55, 120, new Mod[]{}),
    LAR_AUG        (172, ASSAULT, AUTO,  LONG, REL, 348, 0, "LAR_AUG", 0.55, 120, new Mod[]{}),
    ABR_AUG        (173, ASSAULT, BURST, ACOG, REL, 348, 0, "ABR_AUG", 0.55, 120, new Mod[]{}),
    IBR_AUG        (174, ASSAULT, BURST, IRON, REL, 348, 0, "IBR_AUG", 0.55, 120, new Mod[]{}),
    TBR_AUG        (175, ASSAULT, BURST, TACT, REL, 348, 0, "TBR_AUG", 0.55, 120, new Mod[]{}),
    LBR_AUG        (176, ASSAULT, BURST, LONG, REL, 348, 0, "LBR_AUG", 0.55, 120, new Mod[]{}),
    ASR_AUG        (177, ASSAULT, SEMI,  ACOG, REL, 348, 0, "ASR_AUG", 0.55, 120, new Mod[]{}),
    ISR_AUG        (178, ASSAULT, SEMI,  IRON, REL, 348, 0, "ISR_AUG", 0.55, 120, new Mod[]{}),
    TSR_AUG        (179, ASSAULT, SEMI,  TACT, REL, 348, 0, "TSR_AUG", 0.55, 120, new Mod[]{}),
    LSR_AUG        (180, ASSAULT, SEMI,  LONG, REL, 348, 0, "LSR_AUG", 0.55, 120, new Mod[]{}),
    AAS_AUG        (169, ASSAULT, AUTO,  ACOG, SUP, 348, 0, "AAS_AUG", 0.55, 120, new Mod[]{}),
    IAS_AUG        (170, ASSAULT, AUTO,  IRON, SUP, 348, 0, "IAS_AUG", 0.55, 120, new Mod[]{}),
    TAS_AUG        (171, ASSAULT, AUTO,  TACT, SUP, 348, 0, "TAS_AUG", 0.55, 120, new Mod[]{}),
    LAS_AUG        (172, ASSAULT, AUTO,  LONG, SUP, 348, 0, "LAS_AUG", 0.55, 120, new Mod[]{}),
    ABS_AUG        (173, ASSAULT, BURST, ACOG, SUP, 348, 0, "ABS_AUG", 0.55, 120, new Mod[]{}),
    IBS_AUG        (174, ASSAULT, BURST, IRON, SUP, 348, 0, "IBS_AUG", 0.55, 120, new Mod[]{}),
    TBS_AUG        (175, ASSAULT, BURST, TACT, SUP, 348, 0, "TBS_AUG", 0.55, 120, new Mod[]{}),
    LBS_AUG        (176, ASSAULT, BURST, LONG, SUP, 348, 0, "LBS_AUG", 0.55, 120, new Mod[]{}),
    ASS_AUG        (177, ASSAULT, SEMI,  ACOG, SUP, 348, 0, "ASS_AUG", 0.55, 120, new Mod[]{}),
    ISS_AUG        (178, ASSAULT, SEMI,  IRON, SUP, 348, 0, "ISS_AUG", 0.55, 120, new Mod[]{}),
    TSS_AUG        (179, ASSAULT, SEMI,  TACT, SUP, 348, 0, "TSS_AUG", 0.55, 120, new Mod[]{}),
    LSS_AUG        (180, ASSAULT, SEMI,  LONG, SUP, 348, 0, "LSS_AUG", 0.55, 120, new Mod[]{}),
    
    IA_AK47        (20,  ASSAULT, AUTO,  IRON, NA,  281, 0, "AK-47", 0.765, 120, new Mod[]{}),
    AA_AK47        (181, ASSAULT, AUTO,  ACOG, NA,  281, 0, "AA_AK-47", 0.765, 120, new Mod[]{}),
    TA_AK47        (182, ASSAULT, AUTO,  TACT, NA,  281, 0, "TA_AK-47", 0.765, 120, new Mod[]{}),
    IB_AK47        (183, ASSAULT, BURST, IRON, NA,  281, 0, "IB_AK-47", 0.765, 120, new Mod[]{}),
    AB_AK47        (184, ASSAULT, BURST, ACOG, NA,  281, 0, "AB_AK-47", 0.765, 120, new Mod[]{}),
    TB_AK47        (185, ASSAULT, BURST, TACT, NA,  281, 0, "TB_AK-47", 0.765, 120, new Mod[]{}),
    IS_AK47        (186, ASSAULT, SEMI,  IRON, NA,  281, 0, "IS_AK-47", 0.765, 120, new Mod[]{}),
    AS_AK47        (187, ASSAULT, SEMI,  ACOG, NA,  281, 0, "AS_AK-47", 0.765, 120, new Mod[]{}),
    TS_AK47        (188, ASSAULT, SEMI,  TACT, NA,  281, 0, "TS_AK-47", 0.765, 120, new Mod[]{}),
    IAE_AK47       (189, ASSAULT, AUTO,  IRON, EXT, 281, 0, "IAE_AK-47", 0.765, 120, new Mod[]{}),
    AAE_AK47       (190, ASSAULT, AUTO,  ACOG, EXT, 281, 0, "AAE_AK-47", 0.765, 120, new Mod[]{}),
    TAE_AK47       (191, ASSAULT, AUTO,  TACT, EXT, 281, 0, "TAE_AK-47", 0.765, 120, new Mod[]{}),
    IBE_AK47       (192, ASSAULT, BURST, IRON, EXT, 281, 0, "IBE_AK-47", 0.765, 120, new Mod[]{}),
    ABE_AK47       (193, ASSAULT, BURST, ACOG, EXT, 281, 0, "ABE_AK-47", 0.765, 120, new Mod[]{}),
    TBE_AK47       (194, ASSAULT, BURST, TACT, EXT, 281, 0, "TBE_AK-47", 0.765, 120, new Mod[]{}),
    ISE_AK47       (195, ASSAULT, SEMI,  IRON, EXT, 281, 0, "ISE_AK-47", 0.765, 120, new Mod[]{}),
    ASE_AK47       (196, ASSAULT, SEMI,  ACOG, EXT, 281, 0, "ASE_AK-47", 0.765, 120, new Mod[]{}),
    TSE_AK47       (197, ASSAULT, SEMI,  TACT, EXT, 281, 0, "TSE_AK-47", 0.765, 120, new Mod[]{}),
    IAR_AK47       (198, ASSAULT, AUTO,  IRON, REL, 281, 0, "IAR_AK-47", 0.765, 120, new Mod[]{}),
    AAR_AK47       (199, ASSAULT, AUTO,  ACOG, REL, 281, 0, "AAR_AK-47", 0.765, 120, new Mod[]{}),
    TAR_AK47       (200, ASSAULT, AUTO,  TACT, REL, 281, 0, "TAR_AK-47", 0.765, 120, new Mod[]{}),
    IBR_AK47       (201, ASSAULT, BURST, IRON, REL, 281, 0, "IBR_AK-47", 0.765, 120, new Mod[]{}),
    ABR_AK47       (202, ASSAULT, BURST, ACOG, REL, 281, 0, "ABR_AK-47", 0.765, 120, new Mod[]{}),
    TBR_AK47       (203, ASSAULT, BURST, TACT, REL, 281, 0, "TBR_AK-47", 0.765, 120, new Mod[]{}),
    ISR_AK47       (204, ASSAULT, SEMI,  IRON, REL, 281, 0, "ISR_AK-47", 0.765, 120, new Mod[]{}),
    ASR_AK47       (205, ASSAULT, SEMI,  ACOG, REL, 281, 0, "ASR_AK-47", 0.765, 120, new Mod[]{}),
    TSR_AK47       (206, ASSAULT, SEMI,  TACT, REL, 281, 0, "TSR_AK-47", 0.765, 120, new Mod[]{}),
    
    M16         (21,    ASSAULT,  BURST,      IRON,   NA,          362,    0,  "M16",              0.598,  120,    new Mod[]{}),
    GALIL       (22,    ASSAULT,  AUTO,  IRON,         NA,    351,    2,  "GALIL",            0.85,   120,    new Mod[]{}),
    FAMAS       (23,    ASSAULT,  AUTO,  IRON,        NA,     351,    4,  "FAMAS",            0.697,  120,    new Mod[]{}),
    M4A1        (24,    ASSAULT,  AUTO,  IRON,         SUP,    271,    0,  "M4A1",             0.704,  120,    new Mod[]{}),
    SAW         (25,    ASSAULT,  AUTO,  IRON,        NA,     274,    0,  "SAW",              1.215,  120,    new Mod[]{}),
    
    /* SHOTGUNS ***********************************************************************************************************************************/
    Olympia     (26,    SHOTGUN,        SING,IRON,       NA,      291,    0,  "Olympia",          2.43,   120,    new Mod[]{}),
    AA12        (27,    SHOTGUN,        SEMI,  IRON,     NA,        286,    0,  "AA12",             2.0,    120,    new Mod[]{}),
    M3          (28,    SHOTGUN,        PUMP,IRON,       NA,      351,    5,  "M3",               1.88,   120,    new Mod[]{}),
    XM1014      (29,    SHOTGUN,        SEMI,  IRON,      NA,       290,    0,  "XM1014",           2.2,    120,    new Mod[]{});
    
    private static final Random rand = new Random();
    
    private final int uniqueID;
    private final Weapon weaponType;
    private final FireMode firemodeType;
    private final Scope scopeType;
    private final Attatchment suppressorType;
    private final Material material;
    private final byte materialData;
    private final String csWeaponName;
    private final double initialBulletSpread;
    private final int maxDurability;
    private final Mod[] modifiedIDs;
    
    private Gun(final int uniqueID,
                final Weapon weaponType,
                final FireMode firemodeType,
                final Scope scopeType,
                final Attatchment suppressorType,
                final int itemId,
                final int itemData,
                final String crackshotWeaponName,
                final double initialBulletSpread,
                final int maxDurability,
                final Mod[] modifiedIDs)
    {
        this.uniqueID = uniqueID;
        this.weaponType = weaponType;
        this.firemodeType = firemodeType;
        this.scopeType = scopeType;
        this.suppressorType = suppressorType;
        this.material = Material.getMaterial(itemId);
        this.materialData = (byte)itemData;
        this.csWeaponName = crackshotWeaponName;
        this.initialBulletSpread = initialBulletSpread;
        this.maxDurability = maxDurability;
        this.modifiedIDs = modifiedIDs;
    }
    
    public int           getUniqueId()         { return uniqueID; }
    public Weapon        getWeaponType()       { return weaponType; }
    public FireMode      getFireMode()         { return firemodeType; }
    public Scope         getScopeType()        { return scopeType; }
    public Attatchment   getSuppressorType()   { return suppressorType; }
    public Material      getMaterial()         { return material;  }
    public Byte          getMaterialData()     { return materialData; }
    public String        getCSWeaponName()     { return csWeaponName; }
    public double        getInitBulletSpread() { return initialBulletSpread; }
    public int           getMaxDurability()    { return maxDurability; }
    public Mod[]         getModifiedIDs()      { return modifiedIDs;   }
    
    @Override public String toString()         { return csWeaponName;  }
    @Override public int getEnumValue()        { return uniqueID; }

    public int getInitialDurability()
    {
        return this.maxDurability - rand.nextInt(maxDurability/2);
    }
    
    /**
     * Returns the integer of the gun condition.
     * @param durability Current durability of the gun.
     * @return Gun tier integer.
     */
    public int getConditionInt(final int durability)
    {
        final double ratio = (double)durability / (double)this.maxDurability;
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
        
        return (int)((double)(this.maxDurability - durability) * this.weaponType.getRepairPriceWeight() / (1.0 - buildWeight));
    }
    
    public int getUpgradePrice(final ItemStack item)
    {
        final int build = CrackshotLore.getBuild(item);
        
        if (build < 0)
            return -1;
        else if (build == Build.ENHANCED.getEnumValue())
            return 0;
        else
            return (int)((build + 1) * this.weaponType.getUpgradePriceWeight());
    }
    
    public Gun getModifiedGun(final ModType modType)
    {
        for (Mod mod : this.modifiedIDs)
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
        String toReturn[] = new String[this.modifiedIDs.length];
        ArrayList<Mod> list = new ArrayList<>();
        list.addAll(Arrays.asList(this.modifiedIDs));
        Collections.sort(list);
        
        for (int i = 0; i < list.size(); i++)
            toReturn[i] = "$" + list.get(i).getPrice() + " - " + list.get(i).toString();
        
        return toReturn;
    }
    
    static
    public Gun getGun(final int ID)
    {
        return GunAccess.get(ID);
    }
    
    static
    public Gun getGun(final ItemStack item)
    {
        return getGun(CrackshotLore.getWeaponID(item));
    }
}
