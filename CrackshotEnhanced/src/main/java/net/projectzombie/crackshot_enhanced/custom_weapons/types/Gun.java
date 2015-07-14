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
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Suppressor.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
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
    
    I___P228    (2,  PISTOL, SEMI,  IRON, OFF, 351, 8, "P228",     1.09,  120, new Mod[] {new Mod(32, ACOG_), new Mod(33, BURST_), new Mod(35, SUPP_)}),
    A___P228    (32, PISTOL, SEMI,  ACOG, OFF, 351, 8, "A_P228",   1.09,  120, new Mod[] {new Mod(2,  IRON_), new Mod(34, BURST_), new Mod(36, SUPP_)}),
    IB__P228    (33, PISTOL, BURST, IRON, OFF, 351, 8, "B_P228",   1.09,  120, new Mod[] {new Mod(34, ACOG_), new Mod(2,  SEMI_),  new Mod(37, SUPP_)}),
    AB__P228    (34, PISTOL, BURST, ACOG, OFF, 351, 8, "AB_P228",  1.09,  120, new Mod[] {new Mod(33, IRON_), new Mod(32, SEMI_),  new Mod(38, SUPP_)}),
    I_S_P228    (35, PISTOL, SEMI,  IRON, ON,  351, 8, "S_P228",   1.19,  120, new Mod[] {new Mod(32, ACOG_), new Mod(33, BURST_), new Mod(2,  UNSUPP_)}),
    A_S_P228    (36, PISTOL, SEMI,  ACOG, ON,  351, 8, "SA_P228",  1.19,  120, new Mod[] {new Mod(2,  IRON_), new Mod(34, BURST_), new Mod(32, UNSUPP_)}),
    IBS_P228    (37, PISTOL, BURST, IRON, ON,  351, 8, "SB_P228",  1.19,  120, new Mod[] {new Mod(34, ACOG_), new Mod(2,  SEMI_),  new Mod(33, UNSUPP_)}),
    ABS_P228    (38, PISTOL, BURST, ACOG, ON,  351, 8, "SAB_P228", 1.19,  120, new Mod[] {new Mod(33, IRON_), new Mod(32, SEMI_),  new Mod(34, UNSUPP_)}),
    
    I__USP      (3,  PISTOL, SEMI,  IRON, ON,  292, 0, "USP",      1.32,  120, new Mod[] {new Mod(39, ACOG_), new Mod(40, BURST_)}),
    A__USP      (39, PISTOL, SEMI,  ACOG, ON,  292, 0, "A_USP",    1.32,  120, new Mod[] {new Mod(3,  IRON_), new Mod(41, BURST_)}),
    IB_USP      (40, PISTOL, BURST, IRON, ON,  292, 0, "B_USP",    1.32,  120, new Mod[] {new Mod(41, ACOG_), new Mod(3,  SEMI_)}),
    AB_USP      (41, PISTOL, BURST, ACOG, ON,  292, 0, "AB_USP",   1.32,  120, new Mod[] {new Mod(40, IRON_), new Mod(39, SEMI_)}),
    
    /* REVOLVERS **********************************************************************************************************************************/
    Mag44       (4, REVOLVER, SEMI, IRON, NA, 294, 0, "44Mag",      0.78, 120, new Mod[]{}),
    DirtyFrank  (5, REVOLVER, SEMI, ACOG, NA, 336, 0, "DirtyFrank", 0.50, 120, new Mod[]{}),
    
    /* SNIPERS ************************************************************************************************************************************/
    L__Scout     (6,  SNIPER, BOLT, LONG, OFF, 351, 9, "Scout",    0.28, 120, new Mod[] {new Mod(42, TACT_), new Mod(43, ACOG_), new Mod(44, IRON_), new Mod(45, SUPP_)}),
    T__Scout     (42, SNIPER, BOLT, TACT, OFF, 351, 9, "T_Scout",  0.33, 120, new Mod[] {new Mod(6, LONG_),  new Mod(43, ACOG_), new Mod(44, IRON_), new Mod(46, SUPP_)}),
    A__Scout     (43, SNIPER, BOLT, ACOG, OFF, 351, 9, "A_Scout",  0.38, 120, new Mod[] {new Mod(6, LONG_),  new Mod(42, TACT_), new Mod(44, IRON_), new Mod(47, SUPP_)}),
    I__Scout     (44, SNIPER, BOLT, IRON, OFF, 351, 9, "I_Scout",  0.43, 120, new Mod[] {new Mod(6, LONG_),  new Mod(42, TACT_), new Mod(43, ACOG_), new Mod(48, SUPP_)}),
    LS_Scout     (45, SNIPER, BOLT, LONG, ON,  351, 9, "LS_Scout", 0.33, 120, new Mod[] {new Mod(46, TACT_), new Mod(47, ACOG_), new Mod(48, IRON_), new Mod(6, UNSUPP_)}),
    TS_Scout     (46, SNIPER, BOLT, TACT, ON,  351, 9, "TS_Scout", 0.38, 120, new Mod[] {new Mod(45, LONG_), new Mod(47, ACOG_), new Mod(48, IRON_), new Mod(42, UNSUPP_)}),
    AS_Scout     (47, SNIPER, BOLT, ACOG, ON,  351, 9, "AS_Scout", 0.43, 120, new Mod[] {new Mod(45, LONG_), new Mod(46, TACT_), new Mod(48, IRON_), new Mod(43, UNSUPP_)}),
    IS_Scout     (48, SNIPER, BOLT, IRON, ON,  351, 9, "IS_Scout", 0.48, 120, new Mod[] {new Mod(45, LONG_), new Mod(46, TACT_), new Mod(47, ACOG_), new Mod(44, UNSUPP_)}),
    
    //TODO
    L_AWP         (8,  SNIPER, BOLT, LONG, NA,  278, 0,  "AWP",   0.16, 120, new Mod[] {new Mod(49, TACT_), new Mod(50, ACOG_), new Mod(51, IRON_)}),
    T_AWP         (49, SNIPER, BOLT, TACT, NA,  278, 0,  "T_AWP", 0.21, 120, new Mod[] {new Mod(8, LONG_),  new Mod(50, ACOG_), new Mod(51, IRON_)}),
    A_AWP         (50, SNIPER, BOLT, ACOG, NA,  278, 0,  "A_AWP", 0.26, 120, new Mod[] {new Mod(8, LONG_),  new Mod(49, TACT_), new Mod(51, IRON_)}),
    I_AWP         (51, SNIPER, BOLT, IRON, NA,  278, 0,  "I_AWP", 0.31, 120, new Mod[] {new Mod(8, LONG_),  new Mod(59, TACT_), new Mod(50, ACOG_)}),
    
    /* AUTO-SNIPERS *******************************************************************************************************************************/
    LS_G3SG1       (19, AUTO_S, SEMI, LONG, ON,  277, 0,  "G3SG1",    0.48, 120, new Mod[] {new Mod(52, TACT_), new Mod(53, ACOG_), new Mod(54, IRON_), new Mod(55, UNSUPP_)}),
    TS_G3SG1       (52, AUTO_S, SEMI, TACT, ON,  277, 0,  "TS_G3SG1", 0.48, 120, new Mod[] {new Mod(19, LONG_), new Mod(53, ACOG_), new Mod(54, IRON_), new Mod(56, UNSUPP_)}),
    AS_G3SG1       (53, AUTO_S, SEMI, ACOG, ON,  277, 0,  "AS_G3SG1", 0.48, 120, new Mod[] {new Mod(19, LONG_), new Mod(52, TACT_), new Mod(54, IRON_), new Mod(57, UNSUPP_)}),
    IS_G3SG1       (54, AUTO_S, SEMI, IRON, ON,  277, 0,  "IS_G3SG1", 0.48, 120, new Mod[] {new Mod(19, LONG_), new Mod(52, TACT_), new Mod(53, ACOG_), new Mod(58, UNSUPP_)}),   
    L__G3SG1       (55, AUTO_S, SEMI, LONG, OFF, 277, 0,  "L_G3SG1",  0.48, 120, new Mod[] {new Mod(56, TACT_), new Mod(57, ACOG_), new Mod(58, IRON_), new Mod(19, SUPP_)}),
    T__G3SG1       (56, AUTO_S, SEMI, TACT, OFF, 277, 0,  "T_G3SG1",  0.48, 120, new Mod[] {new Mod(55, LONG_), new Mod(57, ACOG_), new Mod(58, IRON_), new Mod(52, SUPP_)}),
    A__G3SG1       (57, AUTO_S, SEMI, ACOG, OFF, 277, 0,  "A_G3SG1",  0.48, 120, new Mod[] {new Mod(55, LONG_), new Mod(56, TACT_), new Mod(58, IRON_), new Mod(53, SUPP_)}),
    I__G3SG1       (58, AUTO_S, SEMI, IRON, OFF, 277, 0,  "I_G3SG1",  0.48, 120, new Mod[] {new Mod(55, LONG_), new Mod(56, TACT_), new Mod(57, ACOG_), new Mod(54, SUPP_)}),
    
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
    
    L_Model70 (11,  HUNTING, BOLT, LONG, NA, 351, 9,  "Model70",   0.41, 120, new Mod[] {new Mod(68, TACT_), new Mod(69, ACOG_), new Mod(70, IRON_)}),
    T_Model70 (68,  HUNTING, BOLT, TACT, NA, 351, 9,  "T_Model70", 0.41, 120, new Mod[] {new Mod(11, LONG_), new Mod(69, ACOG_), new Mod(70, IRON_)}),
    A_Model70 (69,  HUNTING, BOLT, ACOG, NA, 351, 9,  "A_Model70", 0.41, 120, new Mod[] {new Mod(11, LONG_), new Mod(68, TACT_), new Mod(70, IRON_)}),
    I_Model70 (70,  HUNTING, BOLT, IRON, NA, 351, 9,  "I_Model70", 0.41, 120, new Mod[] {new Mod(11, LONG_), new Mod(68, TACT_), new Mod(69, ACOG_)}),
    
    /* SMGS ***************************************************************************************************************************************/
    IA__Mac10 (12, SMG, AUTO,  IRON, OFF, 351, 6, "Mac10",     1.25, 120, new Mod[] {new Mod(71, ACOG_), new Mod(72, BURST_), new Mod(74, SEMI_), new Mod(76, SUPP_)}),
    AA__Mac10 (71, SMG, AUTO,  ACOG, OFF, 351, 6, "AA_Mac10",  1.25, 120, new Mod[] {new Mod(12, IRON_), new Mod(73, BURST_), new Mod(75, SEMI_), new Mod(77, SUPP_)}), 
    IB__Mac10 (72, SMG, BURST, IRON, OFF, 351, 6, "IB_Mac10",  1.25, 120, new Mod[] {new Mod(12, AUTO_), new Mod(73, ACOG_),  new Mod(74, SEMI_), new Mod(78, SUPP_)}),
    AB__Mac10 (73, SMG, BURST, ACOG, OFF, 351, 6, "AB_Mac10",  1.25, 120, new Mod[] {new Mod(71, AUTO_), new Mod(72, IRON_),  new Mod(75, SEMI_), new Mod(79, SUPP_)}),
    IS__Mac10 (74, SMG, SEMI,  IRON, OFF, 351, 6, "IS_Mac10",  1.25, 120, new Mod[] {new Mod(12, AUTO_), new Mod(72, BURST_), new Mod(75, ACOG_), new Mod(80, SUPP_)}),
    AS__Mac10 (75, SMG, SEMI,  ACOG, OFF, 351, 6, "AS_Mac10",  1.25, 120, new Mod[] {new Mod(71, AUTO_), new Mod(73, BURST_), new Mod(74, IRON_), new Mod(81, SUPP_)}),
    IAS_Mac10 (76, SMG, AUTO,  IRON, ON,  351, 6, "IAS_Mac10", 1.25, 120, new Mod[] {new Mod(77, ACOG_), new Mod(78, BURST_), new Mod(80, SEMI_), new Mod(12, UNSUPP_)}),
    AAS_Mac10 (77, SMG, AUTO,  ACOG, ON,  351, 6, "AAS_Mac10", 1.25, 120, new Mod[] {new Mod(76, IRON_), new Mod(79, BURST_), new Mod(81, SEMI_), new Mod(71, UNSUPP_)}),
    IBS_Mac10 (78, SMG, BURST, IRON, ON,  351, 6, "IBS_Mac10", 1.25, 120, new Mod[] {new Mod(76, AUTO_), new Mod(79, ACOG_),  new Mod(80, SEMI_), new Mod(72, UNSUPP_)}),
    ABS_Mac10 (79, SMG, BURST, ACOG, ON,  351, 6, "ABS_Mac10", 1.25, 120, new Mod[] {new Mod(77, AUTO_), new Mod(78, IRON_),  new Mod(81, SEMI_), new Mod(73, UNSUPP_)}),
    ISS_Mac10 (80, SMG, SEMI,  IRON, ON,  351, 6, "ISS_Mac10", 1.25, 120, new Mod[] {new Mod(76, AUTO_), new Mod(78, BURST_), new Mod(81, ACOG_), new Mod(74, UNSUPP_)}),
    ASS_Mac10 (81, SMG, SEMI,  ACOG, ON,  351, 6, "ASS_Mac10", 1.25, 120, new Mod[] {new Mod(77, AUTO_), new Mod(79, BURST_), new Mod(80, IRON_), new Mod(75, UNSUPP_)}),
    
    IA__Ump45 (13, SMG, AUTO,  IRON, OFF, 351, 10, "Ump45",     0.99, 120, new Mod[] {new Mod(82, ACOG_), new Mod(83, BURST_), new Mod(85, SEMI_), new Mod(87, SUPP_)}),
    AA__Ump45 (82, SMG, AUTO,  ACOG, OFF, 351, 10, "AA_Ump45",  0.99, 120, new Mod[] {new Mod(13, IRON_), new Mod(84, BURST_), new Mod(86, SEMI_), new Mod(88, SUPP_)}),
    IB__Ump45 (83, SMG, BURST, IRON, OFF, 351, 10, "IB_Ump45",  0.99, 120, new Mod[] {new Mod(13, AUTO_), new Mod(84, ACOG_),  new Mod(85, SEMI_), new Mod(89, SUPP_)}),
    AB__Ump45 (84, SMG, BURST, ACOG, OFF, 351, 10, "AB_Ump45",  0.99, 120, new Mod[] {new Mod(82, AUTO_), new Mod(83, IRON_),  new Mod(86, SEMI_), new Mod(90, SUPP_)}),
    IS__Ump45 (85, SMG, SEMI,  IRON, OFF, 351, 10, "IS_Ump45",  0.99, 120, new Mod[] {new Mod(13, AUTO_), new Mod(83, BURST_), new Mod(86, ACOG_), new Mod(91, SUPP_)}),
    AS__Ump45 (86, SMG, SEMI,  ACOG, OFF, 351, 10, "AS_Ump45",  0.99, 120, new Mod[] {new Mod(82, AUTO_), new Mod(84, BURST_), new Mod(85, IRON_), new Mod(82, SUPP_)}),
    IAS_Ump45 (87, SMG, AUTO,  IRON, OFF, 351, 10, "IAS_Ump45", 0.99, 120, new Mod[] {new Mod(88, ACOG_), new Mod(89, BURST_), new Mod(91, SEMI_), new Mod(13, UNSUPP_)}),
    AAS_Ump45 (88, SMG, AUTO,  ACOG, OFF, 351, 10, "AAS_Ump45", 0.99, 120, new Mod[] {new Mod(87, IRON_), new Mod(90, BURST_), new Mod(92, SEMI_), new Mod(82, UNSUPP_)}),
    IBS_Ump45 (89, SMG, BURST, IRON, OFF, 351, 10, "IBS_Ump45", 0.99, 120, new Mod[] {new Mod(87, AUTO_), new Mod(90, ACOG_),  new Mod(91, SEMI_), new Mod(83, UNSUPP_)}),
    ABS_Ump45 (90, SMG, BURST, ACOG, OFF, 351, 10, "ABS_Ump45", 0.99, 120, new Mod[] {new Mod(88, AUTO_), new Mod(89, IRON_),  new Mod(92, SEMI_), new Mod(84, UNSUPP_)}),
    ISS_Ump45 (91, SMG, SEMI,  IRON, OFF, 351, 10, "ISS_Ump45", 0.99, 120, new Mod[] {new Mod(87, AUTO_), new Mod(89, BURST_), new Mod(92, ACOG_), new Mod(85, UNSUPP_)}),
    ASS_Ump45 (92, SMG, SEMI,  ACOG, OFF, 351, 10, "ASS_Ump45", 0.99, 120, new Mod[] {new Mod(88, AUTO_), new Mod(90, BURST_), new Mod(91, IRON_), new Mod(86, UNSUPP_)}),
    
    IA_P90 (14, SMG, AUTO,  IRON, NA, 351, 7, "P90",    1.16, 120, new Mod[] {new Mod(93, ACOG_), new Mod(94, BURST_), new Mod(96, SEMI_)}),
    AA_P90 (93, SMG, AUTO,  ACOG, NA, 351, 7, "AA_P90", 1.16, 120, new Mod[] {new Mod(14, IRON_), new Mod(95, BURST_), new Mod(97, SEMI_)}),
    IB_P90 (94, SMG, BURST, IRON, NA, 351, 7, "IB_P90", 1.16, 120, new Mod[] {new Mod(14, AUTO_), new Mod(95, ACOG_),  new Mod(96, SEMI_)}),
    AB_P90 (95, SMG, BURST, ACOG, NA, 351, 7, "AB_P90", 1.16, 120, new Mod[] {new Mod(93, AUTO_), new Mod(94, IRON_),  new Mod(97, SEMI_)}),
    IS_P90 (96, SMG, SEMI,  IRON, NA, 351, 7, "IS_P90", 1.16, 120, new Mod[] {new Mod(14, AUTO_), new Mod(94, BURST_), new Mod(97, ACOG_)}),
    AS_P90 (97, SMG, SEMI,  ACOG, NA, 351, 7, "AS_P90", 1.16, 120, new Mod[] {new Mod(93, AUTO_), new Mod(95, BURST_), new Mod(96, IRON_)}),
    
    IS__MP5 (15,  SMG, SEMI,  IRON, OFF, 285, 0, "MP5",     0.93, 120, new Mod[] {new Mod(98, ACOG_),  new Mod(99, TACT_),  new Mod(100, BURST_), new Mod(103, AUTO_),  new Mod(106, SUPP_)}),
    AS__MP5 (98,  SMG, SEMI,  ACOG, OFF, 285, 0, "AS_MP5",  0.93, 120, new Mod[] {new Mod(15, IRON_),  new Mod(99, TACT_),  new Mod(101, BURST_), new Mod(104, AUTO_),  new Mod(107, SUPP_)}),
    TS__MP5 (99,  SMG, SEMI,  TACT, OFF, 285, 0, "TS_MP5",  0.93, 120, new Mod[] {new Mod(15, IRON_),  new Mod(98, ACOG_),  new Mod(102, BURST_), new Mod(105, AUTO_),  new Mod(108, SUPP_)}),
    IB__MP5 (100, SMG, BURST, IRON, OFF, 285, 0, "IB_MP5",  0.93, 120, new Mod[] {new Mod(101, ACOG_), new Mod(102, TACT_), new Mod(15, SEMI_),   new Mod(103, AUTO_),  new Mod(109, SUPP_)}),
    AB__MP5 (101, SMG, BURST, ACOG, OFF, 285, 0, "AB_MP5",  0.93, 120, new Mod[] {new Mod(100, IRON_), new Mod(102, TACT_), new Mod(98, SEMI_),   new Mod(104, AUTO_),  new Mod(110, SUPP_)}),
    TB__MP5 (102, SMG, BURST, TACT, OFF, 285, 0, "TB_MP5",  0.93, 120, new Mod[] {new Mod(100, IRON_), new Mod(101, ACOG_), new Mod(99, SEMI_),   new Mod(105, AUTO_),  new Mod(111, SUPP_)}),
    IA__MP5 (103, SMG, AUTO,  IRON, OFF, 285, 0, "TA_MP5",  0.93, 120, new Mod[] {new Mod(104, ACOG_), new Mod(105, TACT_), new Mod(15, SEMI_),   new Mod(100, BURST_), new Mod(112, SUPP_)}),
    AA__MP5 (104, SMG, AUTO,  ACOG, OFF, 285, 0, "AA_MP5",  0.93, 120, new Mod[] {new Mod(103, IRON_), new Mod(105, TACT_), new Mod(98, SEMI_),   new Mod(101, BURST_), new Mod(113, SUPP_)}),
    TA__MP5 (105, SMG, AUTO,  TACT, OFF, 285, 0, "TA_MP5",  0.93, 120, new Mod[] {new Mod(103, IRON_), new Mod(104, ACOG_), new Mod(99, SEMI_),   new Mod(102, BURST_), new Mod(114, SUPP_)}),
    ISS_MP5 (106, SMG, SEMI,  IRON, ON,  285, 0, "ISS_MP5", 0.93, 120, new Mod[] {new Mod(107, ACOG_), new Mod(108, TACT_), new Mod(109, BURST_), new Mod(112, AUTO_),  new Mod(15, UNSUPP_)}),
    ASS_MP5 (107, SMG, SEMI,  ACOG, ON,  285, 0, "ASS_MP5", 0.93, 120, new Mod[] {new Mod(106, IRON_), new Mod(108, TACT_), new Mod(110, BURST_), new Mod(113, AUTO_),  new Mod(98, UNSUPP_)}),
    TSS_MP5 (108, SMG, SEMI,  TACT, ON,  285, 0, "TSS_MP5", 0.93, 120, new Mod[] {new Mod(106, IRON_), new Mod(107, ACOG_), new Mod(111, BURST_), new Mod(114, AUTO_),  new Mod(99, UNSUPP_)}),
    IBS_MP5 (109, SMG, BURST, IRON, ON,  285, 0, "IBS_MP5", 0.93, 120, new Mod[] {new Mod(110, ACOG_), new Mod(111, TACT_), new Mod(106, SEMI_),  new Mod(112, AUTO_),  new Mod(100, UNSUPP_)}),
    ABS_MP5 (110, SMG, BURST, ACOG, ON,  285, 0, "ABS_MP5", 0.93, 120, new Mod[] {new Mod(109, IRON_), new Mod(111, TACT_), new Mod(107, SEMI_),  new Mod(113, AUTO_),  new Mod(101, UNSUPP_)}),
    TBS_MP5 (111, SMG, BURST, TACT, ON,  285, 0, "TBS_MP5", 0.93, 120, new Mod[] {new Mod(109, IRON_), new Mod(110, ACOG_), new Mod(108, SEMI_),  new Mod(114, AUTO_),  new Mod(102, UNSUPP_)}),
    IAS_MP5 (112, SMG, AUTO,  IRON, ON,  285, 0, "IAS_MP5", 0.93, 120, new Mod[] {new Mod(113, ACOG_), new Mod(114, TACT_), new Mod(106, SEMI_),  new Mod(109, BURST_), new Mod(103, UNSUPP_)}),
    AAS_MP5 (113, SMG, AUTO,  ACOG, ON,  285, 0, "AAS_MP5", 0.93, 120, new Mod[] {new Mod(112, IRON_), new Mod(114, TACT_), new Mod(107, SEMI_),  new Mod(110, BURST_), new Mod(104, UNSUPP_)}),
    TAS_MP5 (114, SMG, AUTO,  TACT, ON,  285, 0, "TAS_MP5", 0.93, 120, new Mod[] {new Mod(112, IRON_), new Mod(113, ACOG_), new Mod(108, SEMI_),  new Mod(111, BURST_), new Mod(105, UNSUPP_)}),
    
    IA_TMP (16,  SMG, AUTO,  IRON, ON, 284, 0, "TMP",    1.05, 120, new Mod[] {new Mod(115, ACOG_), new Mod(116, BURST_), new Mod(118, SEMI_)}),
    AA_TMP (115, SMG, AUTO,  ACOG, ON, 284, 0, "AA_TMP", 1.05, 120, new Mod[] {new Mod(16, IRON_),  new Mod(117, BURST_), new Mod(119, SEMI_)}),
    IB_TMP (116, SMG, BURST, IRON, ON, 284, 0, "IB_TMP", 1.05, 120, new Mod[] {new Mod(117, ACOG_), new Mod(16,  AUTO_),  new Mod(118, SEMI_)}),
    AB_TMP (117, SMG, BURST, ACOG, ON, 284, 0, "AB_TMP", 1.05, 120, new Mod[] {new Mod(116, IRON_), new Mod(115, AUTO_),  new Mod(119, SEMI_)}),
    IS_TMP (118, SMG, SEMI,  IRON, ON, 284, 0, "IS_TMP", 1.05, 120, new Mod[] {new Mod(119, ACOG_), new Mod(16,  AUTO_),  new Mod(116, BURST_)}),
    AS_TMP (119, SMG, SEMI,  ACOG, ON, 284, 0, "AS_TMP", 1.05, 120, new Mod[] {new Mod(118, IRON_), new Mod(115, AUTO_),  new Mod(117, BURST_)}),
    
    /* ASSAULT RIFLES *****************************************************************************************************************************/
    SG552       (17,    ASSAULT,    AUTO,  ACOG,     NA,      351,    11, "SG552",            0.61,   120,    new Mod[]{}),
    AUG         (18,    ASSAULT,    AUTO,  ACOG,      NA,     348,    0,  "AUG",              0.55,   120,    new Mod[]{}),
    AK47        (20,    ASSAULT,  AUTO,  IRON,      NA,       281,    0,  "AK-47",            0.765,  120,    new Mod[]{}),
    M16         (21,    ASSAULT,  BURST,      IRON,   OFF,          362,    0,  "M16",              0.598,  120,    new Mod[]{}),
    GALIL       (22,    ASSAULT,  AUTO,  IRON,         NA,    351,    2,  "GALIL",            0.85,   120,    new Mod[]{}),
    FAMAS       (23,    ASSAULT,  AUTO,  IRON,        OFF,     351,    4,  "FAMAS",            0.697,  120,    new Mod[]{}),
    M4A1        (24,    ASSAULT,  AUTO,  IRON,         ON,    271,    0,  "M4A1",             0.704,  120,    new Mod[]{}),
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
    private final Suppressor suppressorType;
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
                final Suppressor suppressorType,
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
    public Suppressor    getSuppressorType()   { return suppressorType; }
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
        for (Gun type : Gun.values())
            if (type.uniqueID == ID)
                return type;
        
        return null;
    }
    
    static
    public Gun getGun(final ItemStack item)
    {
        return getGun(CrackshotLore.getWeaponID(item));
    }
}
