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
    //Start at 769 if you're going to add a weapon :)
    
//  Name        GunID, Type, Fire, Sight, Supp, CSname, BSpread
    
    /* PISTOLS ****************************************************************/
    I_Deagle  (0,   DEAGLE, SEMI, IRON, NA,  "Deagle",    1.101),
    A_Deagle  (30,  DEAGLE, SEMI, ACOG, NA,  "A_Deagle",  1.101),
    IR_Deagle (594, DEAGLE, SEMI, IRON, REL, "IR_Deagle", 1.101),
    AR_Deagle (595, DEAGLE, SEMI, ACOG, REL, "AR_Deagle", 1.101),
    IE_Deagle (596, DEAGLE, SEMI, IRON, EXT, "IE_Deagle", 1.101),
    AE_Deagle (597, DEAGLE, SEMI, ACOG, EXT, "AE_Deagle", 1.101),
    II_Deagle (769, DEAGLE, SEMI, IRON, INC, "II_Deagle", 1.101),
    AI_Deagle (770, DEAGLE, SEMI, ACOG, INC, "AI_Deagle", 1.101),
    
    I_Colt45  (1,   COLT45, SEMI,  IRON, NA,  "Colt45",    1.40),
    A_Colt45  (31,  COLT45, SEMI,  ACOG, NA,  "A_Colt45",  1.40),
    IE_Colt45 (590, COLT45, SEMI,  IRON, EXT, "IE_Colt45", 1.40),
    AE_Colt45 (591, COLT45, SEMI,  ACOG, EXT, "AE_Colt45", 1.40),
    IR_Colt45 (592, COLT45, SEMI,  IRON, REL, "IR_Colt45", 1.40),
    AR_Colt45 (593, COLT45, SEMI,  ACOG, REL, "AR_Colt45", 1.40),
    II_Colt45 (771, COLT45, SEMI,  IRON, INC, "II_Colt45", 1.40),
    AI_Colt45 (28,  COLT45, SEMI,  ACOG, INC, "AI_Colt45", 1.40),
    
    I___P228 (2,   P228, SEMI,  IRON, NA,  "P228",    1.09),
    A___P228 (32,  P228, SEMI,  ACOG, NA,  "A_P228",  1.09),
    IB__P228 (33,  P228, BURST, IRON, NA,  "B_P228",  1.09),
    AB__P228 (34,  P228, BURST, ACOG, NA,  "AB_P228", 1.09),
    I_S_P228 (35,  P228, SEMI,  IRON, SUP, "S_P228",  1.19),
    A_S_P228 (36,  P228, SEMI,  ACOG, SUP, "SA_P228", 1.19),
    IBS_P228 (37,  P228, BURST, IRON, SUP, "SB_P228", 1.19),
    ABS_P228 (38,  P228, BURST, ACOG, SUP, "SAB_P228",1.19),
    I_R_P228 (582, P228, SEMI,  IRON, REL, "R_P228",  1.19),
    A_R_P228 (583, P228, SEMI,  ACOG, REL, "RA_P228", 1.19),
    IBR_P228 (584, P228, BURST, IRON, REL, "RB_P228", 1.19),
    ABR_P228 (585, P228, BURST, ACOG, REL, "RAB_P228",1.19),
    I_E_P228 (586, P228, SEMI,  IRON, EXT, "E_P228",  1.19),
    A_E_P228 (587, P228, SEMI,  ACOG, EXT, "EA_P228", 1.19),
    IBE_P228 (588, P228, BURST, IRON, EXT, "EB_P228", 1.19),
    ABE_P228 (589, P228, BURST, ACOG, EXT, "EAB_P228",1.19),
    I_I_P228 (602, P228, SEMI,  IRON, INC, "I_P228",  1.19),
    A_I_P228 (603, P228, SEMI,  ACOG, INC, "IA_P228", 1.19),
    IBI_P228 (604, P228, BURST, IRON, INC, "IB_P228", 1.19),
    ABI_P228 (605, P228, BURST, ACOG, INC, "IAB_P228",1.19),
    
    IS_USP  (3,   USP, SEMI,  IRON, SUP, "USP",     1.32),
    AS_USP  (39,  USP, SEMI,  ACOG, SUP, "A_USP",   1.32),
    IB_USP  (40,  USP, BURST, IRON, SUP, "B_USP",   1.32),
    AB_USP  (41,  USP, BURST, ACOG, SUP, "AB_USP",  1.32),
    ISU_USP (764, USP, SEMI,  IRON, NA,  "ISU_USP", 1.32),
    ASU_USP (765, USP, SEMI,  ACOG, NA,  "ASU_USP", 1.32),
    IBU_USP (766, USP, BURST, IRON, NA,  "IBU_USP", 1.32),
    ABU_USP (767, USP, BURST, ACOG, NA,  "ABU_USP", 1.32),
    ISR_USP (768, USP, SEMI,  IRON, REL, "ISR_USP", 1.32),
    ASR_USP (575, USP, SEMI,  ACOG, REL, "ASR_USP", 1.32),
    IBR_USP (576, USP, BURST, IRON, REL, "IBR_USP", 1.32),
    ABR_USP (577, USP, BURST, ACOG, REL, "ABR_USP", 1.32),
    ISE_USP (578, USP, SEMI,  IRON, EXT, "ISE_USP", 1.32),
    ASE_USP (579, USP, SEMI,  ACOG, EXT, "ASE_USP", 1.32),
    IBE_USP (580, USP, BURST, IRON, EXT, "IBE_USP", 1.32),
    ABE_USP (581, USP, BURST, ACOG, EXT, "ABE_USP", 1.32),
    ISI_USP (606, USP, SEMI,  IRON, INC, "ISI_USP", 1.32),
    ASI_USP (607, USP, SEMI,  ACOG, INC, "ASI_USP", 1.32),
    IBI_USP (608, USP, BURST, IRON, INC, "IBI_USP", 1.32),
    ABI_USP (609, USP, BURST, ACOG, INC, "ABI_USP", 1.32),
    
    /* REVOLVERS **************************************************************/
    I_Mag44  (4,   MAG44, SEMI, IRON, NA,  "44Mag",    0.78),
    A_Mag44  (567, MAG44, SEMI, ACOG, NA,  "A_44Mag",  0.78),
    IR_Mag44 (568, MAG44, SEMI, IRON, REL, "IR_44Mag", 0.78),
    AR_Mag44 (569, MAG44, SEMI, ACOG, REL, "AR_44Mag", 0.78),
    II_Mag44 (610, MAG44, SEMI, IRON, INC, "II_44Mag", 0.78),
    AI_Mag44 (611, MAG44, SEMI, ACOG, INC, "AI_44Mag", 0.78),
    
    A_DirtyFrank  (5,   FRANK, SEMI, ACOG, NA,  "DirtyFrank",    0.50),
    T_DirtyFrank  (570, FRANK, SEMI, TACT, NA,  "T_DirtyFrank",  0.50),
    I_DirtyFrank  (571, FRANK, SEMI, IRON, NA,  "I_DirtyFrank",  0.50),
    AR_DirtyFrank (572, FRANK, SEMI, ACOG, REL, "AR_DirtyFrank", 0.50),
    TR_DirtyFrank (573, FRANK, SEMI, TACT, REL, "TR_DirtyFrank", 0.50),
    IR_DirtyFrank (574, FRANK, SEMI, IRON, REL, "IR_DirtyFrank", 0.50),
    AI_DirtyFrank (612, FRANK, SEMI, ACOG, INC, "AI_DirtyFrank", 0.50),
    TI_DirtyFrank (613, FRANK, SEMI, TACT, INC, "TI_DirtyFrank", 0.50),
    II_DirtyFrank (614, FRANK, SEMI, IRON, INC, "II_DirtyFrank", 0.50),
   
    /* SNIPERS ****************************************************************/
    L__Scout (6,   SCOUT, BOLT, LONG, NA,  "Scout",    0.28),
    T__Scout (42,  SCOUT, BOLT, TACT, NA,  "T_Scout",  0.33),
    A__Scout (43,  SCOUT, BOLT, ACOG, NA,  "A_Scout",  0.38),
    I__Scout (44,  SCOUT, BOLT, IRON, NA,  "I_Scout",  0.43),
    LS_Scout (45,  SCOUT, BOLT, LONG, SUP, "LS_Scout", 0.33),
    TS_Scout (46,  SCOUT, BOLT, TACT, SUP, "TS_Scout", 0.38),
    AS_Scout (47,  SCOUT, BOLT, ACOG, SUP, "AS_Scout", 0.43),
    IS_Scout (48,  SCOUT, BOLT, IRON, SUP, "IS_Scout", 0.48),
    LL_Scout (428, SCOUT, BOLT, LONG, LUB, "LL_Scout", 0.28),
    TL_Scout (429, SCOUT, BOLT, TACT, LUB, "TL_Scout", 0.33),
    AL_Scout (430, SCOUT, BOLT, ACOG, LUB, "AL_Scout", 0.38),
    IL_Scout (431, SCOUT, BOLT, IRON, LUB, "IL_Scout", 0.43),
    LE_Scout (432, SCOUT, BOLT, LONG, EXT, "LE_Scout", 0.28),
    TE_Scout (433, SCOUT, BOLT, TACT, EXT, "TE_Scout", 0.33),
    AE_Scout (434, SCOUT, BOLT, ACOG, EXT, "AE_Scout", 0.38),
    IE_Scout (435, SCOUT, BOLT, IRON, EXT, "IE_Scout", 0.43),
    LI_Scout (615, SCOUT, BOLT, LONG, INC, "LI_Scout", 0.28),
    TI_Scout (616, SCOUT, BOLT, TACT, INC, "TI_Scout", 0.33),
    AI_Scout (617, SCOUT, BOLT, ACOG, INC, "AI_Scout", 0.38),
    II_Scout (618, SCOUT, BOLT, IRON, INC, "II_Scout", 0.43),
    
    L_AWP  (8,   AWP, BOLT, LONG, NA,  "AWP",    0.16),
    T_AWP  (49,  AWP, BOLT, TACT, NA,  "T_AWP",  0.21),
    A_AWP  (50,  AWP, BOLT, ACOG, NA,  "A_AWP",  0.26),
    I_AWP  (51,  AWP, BOLT, IRON, NA,  "I_AWP",  0.31),
    LR_AWP (555, AWP, BOLT, LONG, REL, "LR_AWP", 0.16),
    TR_AWP (556, AWP, BOLT, TACT, REL, "TR_AWP", 0.21),
    AR_AWP (557, AWP, BOLT, ACOG, REL, "AR_AWP", 0.26),
    IR_AWP (558, AWP, BOLT, IRON, REL, "IR_AWP", 0.31),
    LE_AWP (559, AWP, BOLT, LONG, EXT, "LE_AWP", 0.16),
    TE_AWP (560, AWP, BOLT, TACT, EXT, "TE_AWP", 0.21),
    AE_AWP (561, AWP, BOLT, ACOG, EXT, "AE_AWP", 0.26),
    IE_AWP (562, AWP, BOLT, IRON, EXT, "IE_AWP", 0.31),
    LL_AWP (563, AWP, BOLT, LONG, LUB, "LL_AWP", 0.16),
    TL_AWP (564, AWP, BOLT, TACT, LUB, "TL_AWP", 0.21),
    AL_AWP (565, AWP, BOLT, ACOG, LUB, "AL_AWP", 0.26),
    IL_AWP (566, AWP, BOLT, IRON, LUB, "IL_AWP", 0.31),
    LI_AWP (619, AWP, BOLT, LONG, INC, "LI_AWP", 0.16),
    TI_AWP (620, AWP, BOLT, TACT, INC, "TI_AWP", 0.21),
    AI_AWP (621, AWP, BOLT, ACOG, INC, "AI_AWP", 0.26),
    II_AWP (622, AWP, BOLT, IRON, INC, "II_AWP", 0.31),
    
    /* AUTO-SNIPERS ***********************************************************/
    LS_G3SG1 (19,  G3SG1, SEMI, LONG, SUP,  "G3SG1",    0.48),
    TS_G3SG1 (52,  G3SG1, SEMI, TACT, SUP,  "TS_G3SG1", 0.48),
    AS_G3SG1 (53,  G3SG1, SEMI, ACOG, SUP,  "AS_G3SG1", 0.48),
    IS_G3SG1 (54,  G3SG1, SEMI, IRON, SUP,  "IS_G3SG1", 0.48),   
    L__G3SG1 (55,  G3SG1, SEMI, LONG, NA,   "L_G3SG1",  0.48),
    T__G3SG1 (56,  G3SG1, SEMI, TACT, NA,   "T_G3SG1",  0.48),
    A__G3SG1 (57,  G3SG1, SEMI, ACOG, NA,   "A_G3SG1",  0.48),
    I__G3SG1 (58,  G3SG1, SEMI, IRON, NA,   "I_G3SG1",  0.48),
    LR_G3SG1 (547, G3SG1, SEMI, LONG, REL,  "LR_G3SG1", 0.48),
    TR_G3SG1 (548, G3SG1, SEMI, TACT, REL,  "TR_G3SG1", 0.48),
    AR_G3SG1 (549, G3SG1, SEMI, ACOG, REL,  "AR_G3SG1", 0.48),
    IR_G3SG1 (550, G3SG1, SEMI, IRON, REL,  "IR_G3SG1", 0.48),
    LE_G3SG1 (551, G3SG1, SEMI, LONG, EXT,  "LE_G3SG1", 0.48),
    TE_G3SG1 (552, G3SG1, SEMI, TACT, EXT,  "TE_G3SG1", 0.48),
    AE_G3SG1 (553, G3SG1, SEMI, ACOG, EXT,  "AE_G3SG1", 0.48),
    IE_G3SG1 (554, G3SG1, SEMI, IRON, EXT,  "IE_G3SG1", 0.48),
    LI_G3SG1 (623, G3SG1, SEMI, LONG, INC,  "LI_G3SG1", 0.48),
    TI_G3SG1 (624, G3SG1, SEMI, TACT, INC,  "TI_G3SG1", 0.48),
    AI_G3SG1 (625, G3SG1, SEMI, ACOG, INC,  "AI_G3SG1", 0.48),
    II_G3SG1 (626, G3SG1, SEMI, IRON, INC,  "II_G3SG1", 0.48),
    
    L_Dragonuv  (7,   DRAG, SEMI, LONG, NA,  "Dragonuv",    0.46),
    T_Dragonuv  (59,  DRAG, SEMI, TACT, NA,  "T_Dragonuv",  0.46),
    A_Dragonuv  (60,  DRAG, SEMI, ACOG, NA,  "A_Dragonuv",  0.46),
    I_Dragonuv  (61,  DRAG, SEMI, IRON, NA,  "I_Dragonuv",  0.46),
    LR_Dragonuv (539, DRAG, SEMI, LONG, REL, "LR_Dragonuv", 0.46),
    TR_Dragonuv (540, DRAG, SEMI, TACT, REL, "TR_Dragonuv", 0.46),
    AR_Dragonuv (541, DRAG, SEMI, ACOG, REL, "AR_Dragonuv", 0.46),
    IR_Dragonuv (542, DRAG, SEMI, IRON, REL, "IR_Dragonuv", 0.46),
    LE_Dragonuv (543, DRAG, SEMI, LONG, EXT, "LE_Dragonuv", 0.46),
    TE_Dragonuv (544, DRAG, SEMI, TACT, EXT, "TE_Dragonuv", 0.46),
    AE_Dragonuv (545, DRAG, SEMI, ACOG, EXT, "AE_Dragonuv", 0.46),
    IE_Dragonuv (546, DRAG, SEMI, IRON, EXT, "IE_Dragonuv", 0.46),
    LI_Dragonuv (627, DRAG, SEMI, LONG, INC, "LI_Dragonuv", 0.46),
    TI_Dragonuv (628, DRAG, SEMI, TACT, INC, "TI_Dragonuv", 0.46),
    AI_Dragonuv (629, DRAG, SEMI, ACOG, INC, "AI_Dragonuv", 0.46),
    II_Dragonuv (630, DRAG, SEMI, IRON, INC, "II_Dragonuv", 0.46),
    
    /* HUNTING RIFLES *********************************************************/
    L_Remington  (9,   REM, BOLT, LONG, NA,  "Remington700",    0.26),
    T_Remington  (62,  REM, BOLT, TACT, NA,  "T_Remington700",  0.26),
    A_Remington  (63,  REM, BOLT, ACOG, NA,  "A_Remington700",  0.26),
    I_Remington  (64,  REM, BOLT, IRON, NA,  "I_Remington700",  0.26),
    LE_Remington (527, REM, BOLT, LONG, EXT, "LE_Remington700", 0.26),
    TE_Remington (528, REM, BOLT, TACT, EXT, "TE_Remington700", 0.26),
    AE_Remington (529, REM, BOLT, ACOG, EXT, "AE_Remington700", 0.26),
    IE_Remington (530, REM, BOLT, IRON, EXT, "IE_Remington700", 0.26),
    LR_Remington (531, REM, BOLT, LONG, REL, "LR_Remington700", 0.26),
    TR_Remington (532, REM, BOLT, TACT, REL, "TR_Remington700", 0.26),
    AR_Remington (533, REM, BOLT, ACOG, REL, "AR_Remington700", 0.26),
    IR_Remington (534, REM, BOLT, IRON, REL, "IR_Remington700", 0.26),
    LL_Remington (535, REM, BOLT, LONG, LUB, "LL_Remington700", 0.26),
    TL_Remington (536, REM, BOLT, TACT, LUB, "TL_Remington700", 0.26),
    AL_Remington (537, REM, BOLT, ACOG, LUB, "AL_Remington700", 0.26),
    IL_Remington (538, REM, BOLT, IRON, LUB, "IL_Remington700", 0.26),
    LI_Remington (631, REM, BOLT, LONG, INC, "LI_Remington700", 0.26),
    TI_Remington (632, REM, BOLT, TACT, INC, "TI_Remington700", 0.26),
    AI_Remington (633, REM, BOLT, ACOG, INC, "AI_Remington700", 0.26),
    II_Remington (634, REM, BOLT, IRON, INC, "II_Remington700", 0.26),

    I_Springfield  (10,  SPRING, BOLT, IRON, NA,  "M1903",    0.52),
    A_Springfield  (65,  SPRING, BOLT, ACOG, NA,  "A_M1903",  0.52),
    T_Springfield  (66,  SPRING, BOLT, TACT, NA,  "T_M1903",  0.52),
    L_Springfield  (67,  SPRING, BOLT, LONG, NA,  "L_M1903",  0.52),
    IE_Springfield (519, SPRING, BOLT, IRON, EXT, "IE_M1903", 0.52),
    AE_Springfield (520, SPRING, BOLT, ACOG, EXT, "AE_M1903", 0.52),
    TE_Springfield (521, SPRING, BOLT, TACT, EXT, "TE_M1903", 0.52),
    LE_Springfield (522, SPRING, BOLT, LONG, EXT, "LE_M1903", 0.52),
    IR_Springfield (523, SPRING, BOLT, IRON, REL, "IR_M1903", 0.52),
    AR_Springfield (524, SPRING, BOLT, ACOG, REL, "AR_M1903", 0.52),
    TR_Springfield (525, SPRING, BOLT, TACT, REL, "TR_M1903", 0.52),
    LR_Springfield (526, SPRING, BOLT, LONG, REL, "LL_M1903", 0.52),
    IL_Springfield (598, SPRING, BOLT, IRON, LUB, "IL_M1903", 0.52),
    AL_Springfield (599, SPRING, BOLT, ACOG, LUB, "AL_M1903", 0.52),
    TL_Springfield (600, SPRING, BOLT, TACT, LUB, "TL_M1903", 0.52),
    LL_Springfield (601, SPRING, BOLT, LONG, LUB, "LL_M1903", 0.52),
    II_Springfield (635, SPRING, BOLT, IRON, INC, "II_M1903", 0.52),
    AI_Springfield (636, SPRING, BOLT, ACOG, INC, "AI_M1903", 0.52),
    TI_Springfield (637, SPRING, BOLT, TACT, INC, "TI_M1903", 0.52),
    LI_Springfield (638, SPRING, BOLT, LONG, INC, "LI_M1903", 0.52),
    
    L_Model70  (11,   MODEL, BOLT, LONG, NA,  "Model70",    0.41),
    T_Model70  (68,   MODEL, BOLT, TACT, NA,  "T_Model70",  0.41),
    A_Model70  (69,   MODEL, BOLT, ACOG, NA,  "A_Model70",  0.41),
    I_Model70  (70,   MODEL, BOLT, IRON, NA,  "I_Model70",  0.41),
    LR_Model70 (508,  MODEL, BOLT, LONG, REL, "LR_Model70", 0.41),
    TR_Model70 (509,  MODEL, BOLT, TACT, REL, "TR_Model70", 0.41),
    AR_Model70 (510,  MODEL, BOLT, ACOG, REL, "AR_Model70", 0.41),
    IR_Model70 (511,  MODEL, BOLT, IRON, REL, "IR_Model70", 0.41),
    LL_Model70 (512,  MODEL, BOLT, LONG, LUB, "LL_Model70", 0.41),
    TL_Model70 (513,  MODEL, BOLT, TACT, LUB, "TL_Model70", 0.41),
    AL_Model70 (514,  MODEL, BOLT, ACOG, LUB, "AL_Model70", 0.41),
    IL_Model70 (515,  MODEL, BOLT, IRON, LUB, "IL_Model70", 0.41),
    LE_Model70 (516,  MODEL, BOLT, LONG, EXT, "LE_Model70", 0.41),
    TE_Model70 (517,  MODEL, BOLT, TACT, EXT, "TE_Model70", 0.41),
    AE_Model70 (518,  MODEL, BOLT, ACOG, EXT, "AE_Model70", 0.41),
    IE_Model70 (25,   MODEL, BOLT, IRON, EXT, "IE_Model70", 0.41),
    LI_Model70 (639,  MODEL, BOLT, LONG, INC, "LI_Model70", 0.41),
    TI_Model70 (640,  MODEL, BOLT, TACT, INC, "TI_Model70", 0.41),
    AI_Model70 (641,  MODEL, BOLT, ACOG, INC, "AI_Model70", 0.41),
    II_Model70 (642,  MODEL, BOLT, IRON, INC, "II_Model70", 0.41),
    
    /* SMGS *******************************************************************/
    IA__Mac10 (12,  MAC10, AUTO,  IRON, NA,  "Mac10",     1.25),
    AA__Mac10 (71,  MAC10, AUTO,  ACOG, NA,  "AA_Mac10",  1.25), 
    IB__Mac10 (72,  MAC10, BURST, IRON, NA,  "IB_Mac10",  1.25),
    AB__Mac10 (73,  MAC10, BURST, ACOG, NA,  "AB_Mac10",  1.25),
    IS__Mac10 (74,  MAC10, SEMI,  IRON, NA,  "IS_Mac10",  1.25),
    AS__Mac10 (75,  MAC10, SEMI,  ACOG, NA,  "AS_Mac10",  1.25),
    IAS_Mac10 (76,  MAC10, AUTO,  IRON, SUP, "IAS_Mac10", 1.25),
    AAS_Mac10 (77,  MAC10, AUTO,  ACOG, SUP, "AAS_Mac10", 1.25),
    IBS_Mac10 (78,  MAC10, BURST, IRON, SUP, "IBS_Mac10", 1.25),
    ABS_Mac10 (79,  MAC10, BURST, ACOG, SUP, "ABS_Mac10", 1.25),
    ISS_Mac10 (80,  MAC10, SEMI,  IRON, SUP, "ISS_Mac10", 1.25),
    ASS_Mac10 (81,  MAC10, SEMI,  ACOG, SUP, "ASS_Mac10", 1.25),
    IAE_Mac10 (496, MAC10, AUTO,  IRON, EXT, "IAE_Mac10", 1.25),
    AAE_Mac10 (497, MAC10, AUTO,  ACOG, EXT, "AAE_Mac10", 1.25),
    IBE_Mac10 (498, MAC10, BURST, IRON, EXT, "IBE_Mac10", 1.25),
    ABE_Mac10 (499, MAC10, BURST, ACOG, EXT, "ABE_Mac10", 1.25),
    ISE_Mac10 (500, MAC10, SEMI,  IRON, EXT, "ISE_Mac10", 1.25),
    ASE_Mac10 (501, MAC10, SEMI,  ACOG, EXT, "ASE_Mac10", 1.25),
    IAR_Mac10 (502, MAC10, AUTO,  IRON, REL, "IAR_Mac10", 1.25),
    AAR_Mac10 (503, MAC10, AUTO,  ACOG, REL, "AAR_Mac10", 1.25),
    IBR_Mac10 (504, MAC10, BURST, IRON, REL, "IBR_Mac10", 1.25),
    ABR_Mac10 (505, MAC10, BURST, ACOG, REL, "ABR_Mac10", 1.25),
    ISR_Mac10 (506, MAC10, SEMI,  IRON, REL, "ISR_Mac10", 1.25),
    ASR_Mac10 (507, MAC10, SEMI,  ACOG, REL, "ASR_Mac10", 1.25),
    IAI_Mac10 (643, MAC10, AUTO,  IRON, INC, "IAI_Mac10", 1.25),
    AAI_Mac10 (644, MAC10, AUTO,  ACOG, INC, "AAI_Mac10", 1.25),
    IBI_Mac10 (645, MAC10, BURST, IRON, INC, "IBI_Mac10", 1.25),
    ABI_Mac10 (646, MAC10, BURST, ACOG, INC, "ABI_Mac10", 1.25),
    ISI_Mac10 (647, MAC10, SEMI,  IRON, INC, "ISI_Mac10", 1.25),
    ASI_Mac10 (648, MAC10, SEMI,  ACOG, INC, "ASI_Mac10", 1.25),
    
    IA__Ump45 (13,  UMP45, AUTO,  IRON, NA,  "Ump45",     0.99),
    AA__Ump45 (82,  UMP45, AUTO,  ACOG, NA,  "AA_Ump45",  0.99),
    IB__Ump45 (83,  UMP45, BURST, IRON, NA,  "IB_Ump45",  0.99),
    AB__Ump45 (84,  UMP45, BURST, ACOG, NA,  "AB_Ump45",  0.99),
    IS__Ump45 (85,  UMP45, SEMI,  IRON, NA,  "IS_Ump45",  0.99),
    AS__Ump45 (86,  UMP45, SEMI,  ACOG, NA,  "AS_Ump45",  0.99),
    IAS_Ump45 (87,  UMP45, AUTO,  IRON, SUP, "IAS_Ump45", 0.99),
    AAS_Ump45 (88,  UMP45, AUTO,  ACOG, SUP, "AAS_Ump45", 0.99),
    IBS_Ump45 (89,  UMP45, BURST, IRON, SUP, "IBS_Ump45", 0.99),
    ABS_Ump45 (90,  UMP45, BURST, ACOG, SUP, "ABS_Ump45", 0.99),
    ISS_Ump45 (91,  UMP45, SEMI,  IRON, SUP, "ISS_Ump45", 0.99),
    ASS_Ump45 (92,  UMP45, SEMI,  ACOG, SUP, "ASS_Ump45", 0.99),
    IAE_Ump45 (484, UMP45, AUTO,  IRON, EXT, "IAE_Ump45", 0.99),
    AAE_Ump45 (485, UMP45, AUTO,  ACOG, EXT, "AAE_Ump45", 0.99),
    IBE_Ump45 (486, UMP45, BURST, IRON, EXT, "IBE_Ump45", 0.99),
    ABE_Ump45 (487, UMP45, BURST, ACOG, EXT, "ABE_Ump45", 0.99),
    ISE_Ump45 (488, UMP45, SEMI,  IRON, EXT, "ISE_Ump45", 0.99),
    ASE_Ump45 (489, UMP45, SEMI,  ACOG, EXT, "ASE_Ump45", 0.99),
    IAR_Ump45 (490, UMP45, AUTO,  IRON, REL, "IAR_Ump45", 0.99),
    AAR_Ump45 (491, UMP45, AUTO,  ACOG, REL, "AAR_Ump45", 0.99),
    IBR_Ump45 (492, UMP45, BURST, IRON, REL, "IBR_Ump45", 0.99),
    ABR_Ump45 (493, UMP45, BURST, ACOG, REL, "ABR_Ump45", 0.99),
    ISR_Ump45 (494, UMP45, SEMI,  IRON, REL, "ISR_Ump45", 0.99),
    ASR_Ump45 (495, UMP45, SEMI,  ACOG, REL, "ASR_Ump45", 0.99),
    IAI_Ump45 (649, UMP45, AUTO,  IRON, INC, "IAI_Ump45", 0.99),
    AAI_Ump45 (650, UMP45, AUTO,  ACOG, INC, "AAI_Ump45", 0.99),
    IBI_Ump45 (651, UMP45, BURST, IRON, INC, "IBI_Ump45", 0.99),
    ABI_Ump45 (652, UMP45, BURST, ACOG, INC, "ABI_Ump45", 0.99),
    ISI_Ump45 (653, UMP45, SEMI,  IRON, INC, "ISI_Ump45", 0.99),
    ASI_Ump45 (654, UMP45, SEMI,  ACOG, INC, "ASI_Ump45", 0.99),
    
    IA_P90  (14,  P90, AUTO,  IRON, NA,  "P90",     1.16),
    AA_P90  (93,  P90, AUTO,  ACOG, NA,  "AA_P90",  1.16),
    IB_P90  (94,  P90, BURST, IRON, NA,  "IB_P90",  1.16),
    AB_P90  (95,  P90, BURST, ACOG, NA,  "AB_P90",  1.16),
    IS_P90  (96,  P90, SEMI,  IRON, NA,  "IS_P90",  1.16),
    AS_P90  (97,  P90, SEMI,  ACOG, NA,  "AS_P90",  1.16),
    IAE_P90 (472, P90, AUTO,  IRON, EXT, "IAE_P90", 1.16),
    AAE_P90 (473, P90, AUTO,  ACOG, EXT, "AAE_P90", 1.16),
    IBE_P90 (474, P90, BURST, IRON, EXT, "IBE_P90", 1.16),
    ABE_P90 (475, P90, BURST, ACOG, EXT, "ABE_P90", 1.16),
    ISE_P90 (476, P90, SEMI,  IRON, EXT, "ISE_P90", 1.16),
    ASE_P90 (477, P90, SEMI,  ACOG, EXT, "ASE_P90", 1.16),
    IAR_P90 (478, P90, AUTO,  IRON, REL, "IAR_P90", 1.16),
    AAR_P90 (479, P90, AUTO,  ACOG, REL, "AAR_P90", 1.16),
    IBR_P90 (480, P90, BURST, IRON, REL, "IBR_P90", 1.16),
    ABR_P90 (481, P90, BURST, ACOG, REL, "ABR_P90", 1.16),
    ISR_P90 (482, P90, SEMI,  IRON, REL, "ISR_P90", 1.16),
    ASR_P90 (483, P90, SEMI,  ACOG, REL, "ASR_P90", 1.16),
    IAI_P90 (655, P90, AUTO,  IRON, INC, "IAI_P90", 1.16),
    AAI_P90 (656, P90, AUTO,  ACOG, INC, "AAI_P90", 1.16),
    IBI_P90 (657, P90, BURST, IRON, INC, "IBI_P90", 1.16),
    ABI_P90 (658, P90, BURST, ACOG, INC, "ABI_P90", 1.16),
    ISI_P90 (659, P90, SEMI,  IRON, INC, "ISI_P90", 1.16),
    ASI_P90 (660, P90, SEMI,  ACOG, INC, "ASI_P90", 1.16),
    
    IS__MP5 (15,  MP5, SEMI,  IRON, NA,  "MP5",     0.93),
    AS__MP5 (98,  MP5, SEMI,  ACOG, NA,  "AS_MP5",  0.93),
    TS__MP5 (99,  MP5, SEMI,  TACT, NA,  "TS_MP5",  0.93),
    IB__MP5 (100, MP5, BURST, IRON, NA,  "IB_MP5",  0.93),
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
    ISE_MP5 (454, MP5, SEMI,  IRON, EXT, "ISE_MP5", 0.93),
    ASE_MP5 (455, MP5, SEMI,  ACOG, EXT, "ASE_MP5", 0.93),
    TSE_MP5 (456, MP5, SEMI,  TACT, EXT, "TSE_MP5", 0.93),
    IBE_MP5 (457, MP5, BURST, IRON, EXT, "IBE_MP5", 0.93),
    ABE_MP5 (458, MP5, BURST, ACOG, EXT, "ABE_MP5", 0.93),
    TBE_MP5 (459, MP5, BURST, TACT, EXT, "TBE_MP5", 0.93),
    IAE_MP5 (460, MP5, AUTO,  IRON, EXT, "IAE_MP5", 0.93),
    AAE_MP5 (461, MP5, AUTO,  ACOG, EXT, "AAE_MP5", 0.93),
    TAE_MP5 (462, MP5, AUTO,  TACT, EXT, "TAE_MP5", 0.93),
    ISR_MP5 (463, MP5, SEMI,  IRON, REL, "ISR_MP5", 0.93),
    ASR_MP5 (464, MP5, SEMI,  ACOG, REL, "ASR_MP5", 0.93),
    TSR_MP5 (465, MP5, SEMI,  TACT, REL, "TSR_MP5", 0.93),
    IBR_MP5 (466, MP5, BURST, IRON, REL, "IBR_MP5", 0.93),
    ABR_MP5 (467, MP5, BURST, ACOG, REL, "ABR_MP5", 0.93),
    TBR_MP5 (468, MP5, BURST, TACT, REL, "TBR_MP5", 0.93),
    IAR_MP5 (469, MP5, AUTO,  IRON, REL, "IAR_MP5", 0.93),
    AAR_MP5 (470, MP5, AUTO,  ACOG, REL, "AAR_MP5", 0.93),
    TAR_MP5 (471, MP5, AUTO,  TACT, REL, "TAR_MP5", 0.93),
    ASI_MP5 (661, MP5, SEMI,  ACOG, INC, "ASI_MP5", 0.93),
    TSI_MP5 (662, MP5, SEMI,  TACT, INC, "TSI_MP5", 0.93),
    IBI_MP5 (663, MP5, BURST, IRON, INC, "IBI_MP5", 0.93),
    ABI_MP5 (664, MP5, BURST, ACOG, INC, "ABI_MP5", 0.93),
    TBI_MP5 (665, MP5, BURST, TACT, INC, "TBI_MP5", 0.93),
    IAI_MP5 (666, MP5, AUTO,  IRON, INC, "IAI_MP5", 0.93),
    AAI_MP5 (667, MP5, AUTO,  ACOG, INC, "AAI_MP5", 0.93),
    TAI_MP5 (668, MP5, AUTO,  TACT, INC, "TAI_MP5", 0.93),
    
    IAS_TMP (16,  TMP, AUTO,  IRON, SUP, "TMP",     1.05),
    AAS_TMP (115, TMP, AUTO,  ACOG, SUP, "AAS_TMP", 1.05),
    IBS_TMP (116, TMP, BURST, IRON, SUP, "IBS_TMP", 1.05),
    ABS_TMP (117, TMP, BURST, ACOG, SUP, "ABS_TMP", 1.05),
    ISS_TMP (118, TMP, SEMI,  IRON, SUP, "ISS_TMP", 1.05),
    ASS_TMP (119, TMP, SEMI,  ACOG, SUP, "ASS_TMP", 1.05),
    IAU_TMP (436, TMP, AUTO,  IRON, NA,  "IAU_TMP", 1.05),
    AAU_TMP (437, TMP, AUTO,  ACOG, NA,  "AAU_TMP", 1.05),
    IBU_TMP (438, TMP, BURST, IRON, NA,  "IBU_TMP", 1.05),
    ABU_TMP (439, TMP, BURST, ACOG, NA,  "ABU_TMP", 1.05),
    ISU_TMP (440, TMP, SEMI,  IRON, NA,  "ISU_TMP", 1.05),
    ASU_TMP (441, TMP, SEMI,  ACOG, NA,  "ASU_TMP", 1.05),
    IAE_TMP (442, TMP, AUTO,  IRON, EXT, "IAE_TMP", 1.05),
    AAE_TMP (443, TMP, AUTO,  ACOG, EXT, "AAE_TMP", 1.05),
    IBE_TMP (444, TMP, BURST, IRON, EXT, "IBE_TMP", 1.05),
    ABE_TMP (445, TMP, BURST, ACOG, EXT, "ABE_TMP", 1.05),
    ISE_TMP (446, TMP, SEMI,  IRON, EXT, "ISE_TMP", 1.05),
    ASE_TMP (447, TMP, SEMI,  ACOG, EXT, "ASE_TMP", 1.05),
    IAR_TMP (448, TMP, AUTO,  IRON, REL, "IAR_TMP", 1.05),
    AAR_TMP (449, TMP, AUTO,  ACOG, REL, "AAR_TMP", 1.05),
    IBR_TMP (450, TMP, BURST, IRON, REL, "IBR_TMP", 1.05),
    ABR_TMP (451, TMP, BURST, ACOG, REL, "ABR_TMP", 1.05),
    ISR_TMP (452, TMP, SEMI,  IRON, REL, "ISR_TMP", 1.05),
    ASR_TMP (453, TMP, SEMI,  ACOG, REL, "ASR_TMP", 1.05),
    IAI_TMP (669, TMP, AUTO,  IRON, INC, "IAI_TMP", 1.05),
    AAI_TMP (670, TMP, AUTO,  ACOG, INC, "AAI_TMP", 1.05),
    IBI_TMP (671, TMP, BURST, IRON, INC, "IBI_TMP", 1.05),
    ABI_TMP (672, TMP, BURST, ACOG, INC, "ABI_TMP", 1.05),
    ISI_TMP (673, TMP, SEMI,  IRON, INC, "ISI_TMP", 1.05),
    ASI_TMP (674, TMP, SEMI,  ACOG, INC, "ASI_TMP", 1.05),
    
    /* ASSAULT RIFLES *********************************************************/
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
    AAI_SG552 (675, SG552, AUTO,  ACOG, INC, "AAI_SG552", 0.61),
    IAI_SG552 (676, SG552, AUTO,  IRON, INC, "IAI_SG552", 0.61),
    TAI_SG552 (677, SG552, AUTO,  TACT, INC, "TAI_SG552", 0.61),
    ABI_SG552 (678, SG552, BURST, ACOG, INC, "ABI_SG552", 0.61),
    IBI_SG552 (679, SG552, BURST, IRON, INC, "IBI_SG552", 0.61),
    TBI_SG552 (680, SG552, BURST, TACT, INC, "TBI_SG552", 0.61),
    ASI_SG552 (681, SG552, SEMI,  ACOG, INC, "ASI_SG552", 0.61),
    ISI_SG552 (682, SG552, SEMI,  IRON, INC, "ISI_SG552", 0.61),
    TSI_SG552 (683, SG552, SEMI,  TACT, INC, "TSI_SG552", 0.61),
    
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
    AAI_AUG (684, AUG, AUTO,  ACOG, INC, "AAI_AUG", 0.55),
    IAI_AUG (685, AUG, AUTO,  IRON, INC, "IAI_AUG", 0.55),
    TAI_AUG (686, AUG, AUTO,  TACT, INC, "TAI_AUG", 0.55),
    LAI_AUG (687, AUG, AUTO,  LONG, INC, "LAI_AUG", 0.55),
    ABI_AUG (688, AUG, BURST, ACOG, INC, "ABI_AUG", 0.55),
    IBI_AUG (689, AUG, BURST, IRON, INC, "IBI_AUG", 0.55),
    TBI_AUG (690, AUG, BURST, TACT, INC, "TBI_AUG", 0.55),
    LBI_AUG (691, AUG, BURST, LONG, INC, "LBI_AUG", 0.55),
    ASI_AUG (692, AUG, SEMI,  ACOG, INC, "ASI_AUG", 0.55),
    ISI_AUG (693, AUG, SEMI,  IRON, INC, "ISI_AUG", 0.55),
    TSI_AUG (694, AUG, SEMI,  TACT, INC, "TSI_AUG", 0.55),
    LSI_AUG (695, AUG, SEMI,  LONG, INC, "LSI_AUG", 0.55),
    
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
    IAI_AK47 (696, AK47, AUTO,  IRON, INC, "IAI_AK-47", 0.765),
    AAI_AK47 (697, AK47, AUTO,  ACOG, INC, "AAI_AK-47", 0.765),
    TAI_AK47 (698, AK47, AUTO,  TACT, INC, "TAI_AK-47", 0.765),
    IBI_AK47 (699, AK47, BURST, IRON, INC, "IBI_AK-47", 0.765),
    ABI_AK47 (700, AK47, BURST, ACOG, INC, "ABI_AK-47", 0.765),
    TBI_AK47 (701, AK47, BURST, TACT, INC, "TBI_AK-47", 0.765),
    ISI_AK47 (702, AK47, SEMI,  IRON, INC, "ISI_AK-47", 0.765),
    ASI_AK47 (703, AK47, SEMI,  ACOG, INC, "ASI_AK-47", 0.765),
    TSI_AK47 (704, AK47, SEMI,  TACT, INC, "TSI_AK-47", 0.765),
    
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
    IBE_M16 (233, M16, BURST, IRON, EXT, "IBE_M16", 0.598),
    ABE_M16 (234, M16, BURST, ACOG, EXT, "ABE_M16", 0.598),
    TBE_M16 (235, M16, BURST, TACT, EXT, "TBE_M16", 0.598),
    ISE_M16 (236, M16, SEMI,  IRON, EXT, "ISE_M16", 0.598),
    ASE_M16 (237, M16, SEMI,  ACOG, EXT, "ASE_M16", 0.598),
    TSE_M16 (238, M16, SEMI,  TACT, EXT, "TSE_M16", 0.598),
    IAE_M16 (239, M16, AUTO,  IRON, EXT, "IAE_M16", 0.598),
    AAE_M16 (240, M16, AUTO,  ACOG, EXT, "AAE_M16", 0.598),
    TAE_M16 (241, M16, AUTO,  TACT, EXT, "TAE_M16", 0.598),
    IBI_M16 (705, M16, BURST, IRON, INC, "IBI_M16", 0.598),
    ABI_M16 (706, M16, BURST, ACOG, INC, "ABI_M16", 0.598),
    TBI_M16 (707, M16, BURST, TACT, INC, "TBI_M16", 0.598),
    ISI_M16 (708, M16, SEMI,  IRON, INC, "ISI_M16", 0.598),
    ASI_M16 (709, M16, SEMI,  ACOG, INC, "ASI_M16", 0.598),
    TSI_M16 (710, M16, SEMI,  TACT, INC, "TSI_M16", 0.598),
    IAI_M16 (711, M16, AUTO,  IRON, INC, "IAI_M16", 0.598),
    AAI_M16 (712, M16, AUTO,  ACOG, INC, "AAI_M16", 0.598),
    TAI_M16 (713, M16, AUTO,  TACT, INC, "TAI_M16", 0.598),
    
    IA_GALIL  (22,  GALIL, AUTO,  IRON,  NA, "GALIL",    0.85),
    AA_GALIL  (254, GALIL, AUTO,  ACOG,  NA, "AA_GALIL", 0.85),
    TA_GALIL  (255, GALIL, AUTO,  TACT,  NA, "TA_GALIL", 0.85),
    IB_GALIL  (256, GALIL, BURST, IRON,  NA, "IB_GALIL", 0.85),
    AB_GALIL  (257, GALIL, BURST, ACOG,  NA, "AB_GALIL", 0.85),
    TB_GALIL  (258, GALIL, BURST, TACT,  NA, "TB_GALIL", 0.85),
    IS_GALIL  (259, GALIL, SEMI,  IRON,  NA, "IS_GALIL", 0.85),
    AS_GALIL  (260, GALIL, SEMI,  ACOG,  NA, "AS_GALIL", 0.85),
    TS_GALIL  (261, GALIL, SEMI,  TACT,  NA, "TS_GALIL", 0.85),
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
    IAI_GALIL (714, GALIL, AUTO,  IRON, INC, "IAI_GALIL", 0.85),
    AAI_GALIL (715, GALIL, AUTO,  ACOG, INC, "AAI_GALIL", 0.85),
    TAI_GALIL (716, GALIL, AUTO,  TACT, INC, "TAI_GALIL", 0.85),
    IBI_GALIL (717, GALIL, BURST, IRON, INC, "IBI_GALIL", 0.85),
    ABI_GALIL (718, GALIL, BURST, ACOG, INC, "ABI_GALIL", 0.85),
    TBI_GALIL (719, GALIL, BURST, TACT, INC, "TBI_GALIL", 0.85),
    ISI_GALIL (720, GALIL, SEMI,  IRON, INC, "ISI_GALIL", 0.85),
    ASI_GALIL (721, GALIL, SEMI,  ACOG, INC, "ASI_GALIL", 0.85),
    TSI_GALIL (722, GALIL, SEMI,  TACT, INC, "TSI_GALIL", 0.85),
    
    IA_FAMAS  (23,  FAMAS, AUTO,  IRON, NA,  "FAMAS",     0.697),
    AA_FAMAS  (280, FAMAS, AUTO,  ACOG, NA,  "AA_FAMAS",  0.697),
    TA_FAMAS  (281, FAMAS, AUTO,  TACT, NA,  "TA_FAMAS",  0.697),
    LA_FAMAS  (282, FAMAS, AUTO,  LONG, NA,  "LA_FAMAS",  0.697),
    IB_FAMAS  (283, FAMAS, BURST, IRON, NA,  "IB_FAMAS",  0.697),
    AB_FAMAS  (284, FAMAS, BURST, ACOG, NA,  "AB_FAMAS",  0.697),
    TB_FAMAS  (285, FAMAS, BURST, TACT, NA,  "TB_FAMAS",  0.697),
    LB_FAMAS  (286, FAMAS, BURST, LONG, NA,  "LB_FAMAS",  0.697),
    IS_FAMAS  (287, FAMAS, SEMI,  IRON, NA,  "IS_FAMAS",  0.697),
    AS_FAMAS  (288, FAMAS, SEMI,  ACOG, NA,  "AS_FAMAS",  0.697),
    TS_FAMAS  (289, FAMAS, SEMI,  TACT, NA,  "TS_FAMAS",  0.697),
    LS_FAMAS  (290, FAMAS, SEMI,  LONG, NA,  "LS_FAMAS",  0.697),
    IAS_FAMAS (291, FAMAS, AUTO,  IRON, SUP, "IAS_FAMAS", 0.697),
    AAS_FAMAS (292, FAMAS, AUTO,  ACOG, SUP, "AAS_FAMAS", 0.697),
    TAS_FAMAS (293, FAMAS, AUTO,  TACT, SUP, "TAS_FAMAS", 0.697),
    LAS_FAMAS (294, FAMAS, AUTO,  LONG, SUP, "LAS_FAMAS", 0.697),
    IBS_FAMAS (295, FAMAS, BURST, IRON, SUP, "IBS_FAMAS", 0.697),
    ABS_FAMAS (296, FAMAS, BURST, ACOG, SUP, "ABS_FAMAS", 0.697),
    TBS_FAMAS (297, FAMAS, BURST, TACT, SUP, "TBS_FAMAS", 0.697),
    LBS_FAMAS (298, FAMAS, BURST, LONG, SUP, "LBS_FAMAS", 0.697),
    ISS_FAMAS (299, FAMAS, SEMI,  IRON, SUP, "ISS_FAMAS", 0.697),
    ASS_FAMAS (300, FAMAS, SEMI,  ACOG, SUP, "ASS_FAMAS", 0.697),
    TSS_FAMAS (301, FAMAS, SEMI,  TACT, SUP, "TSS_FAMAS", 0.697),
    LSS_FAMAS (302, FAMAS, SEMI,  LONG, SUP, "LSS_FAMAS", 0.697),
    IAR_FAMAS (303, FAMAS, AUTO,  IRON, REL, "IAR_FAMAS", 0.697),
    AAR_FAMAS (304, FAMAS, AUTO,  ACOG, REL, "AAR_FAMAS", 0.697),
    TAR_FAMAS (305, FAMAS, AUTO,  TACT, REL, "TAR_FAMAS", 0.697),
    LAR_FAMAS (306, FAMAS, AUTO,  LONG, REL, "LAR_FAMAS", 0.697),
    IBR_FAMAS (307, FAMAS, BURST, IRON, REL, "IBR_FAMAS", 0.697),
    ABR_FAMAS (308, FAMAS, BURST, ACOG, REL, "ABR_FAMAS", 0.697),
    TBR_FAMAS (309, FAMAS, BURST, TACT, REL, "TBR_FAMAS", 0.697),
    LBR_FAMAS (310, FAMAS, BURST, LONG, REL, "LBR_FAMAS", 0.697),
    ISR_FAMAS (311, FAMAS, SEMI,  IRON, REL, "ISR_FAMAS", 0.697),
    ASR_FAMAS (312, FAMAS, SEMI,  ACOG, REL, "ASR_FAMAS", 0.697),
    TSR_FAMAS (313, FAMAS, SEMI,  TACT, REL, "TSR_FAMAS", 0.697),
    LSR_FAMAS (314, FAMAS, SEMI,  LONG, REL, "LSR_FAMAS", 0.697),
    IAE_FAMAS (315, FAMAS, AUTO,  IRON, EXT, "IAE_FAMAS", 0.697),
    AAE_FAMAS (316, FAMAS, AUTO,  ACOG, EXT, "AAE_FAMAS", 0.697),
    TAE_FAMAS (317, FAMAS, AUTO,  TACT, EXT, "TAE_FAMAS", 0.697),
    LAE_FAMAS (318, FAMAS, AUTO,  LONG, EXT, "LAE_FAMAS", 0.697),
    IBE_FAMAS (319, FAMAS, BURST, IRON, EXT, "IBE_FAMAS", 0.697),
    ABE_FAMAS (320, FAMAS, BURST, ACOG, EXT, "ABE_FAMAS", 0.697),
    TBE_FAMAS (321, FAMAS, BURST, TACT, EXT, "TBE_FAMAS", 0.697),
    LBE_FAMAS (322, FAMAS, BURST, LONG, EXT, "LBE_FAMAS", 0.697),
    ISE_FAMAS (323, FAMAS, SEMI,  IRON, EXT, "ISE_FAMAS", 0.697),
    ASE_FAMAS (324, FAMAS, SEMI,  ACOG, EXT, "ASE_FAMAS", 0.697),
    TSE_FAMAS (325, FAMAS, SEMI,  TACT, EXT, "TSE_FAMAS", 0.697),
    LSE_FAMAS (326, FAMAS, SEMI,  LONG, EXT, "LSE_FAMAS", 0.697),
    IAI_FAMAS (723, FAMAS, AUTO,  IRON, INC, "IAI_FAMAS", 0.697),
    AAI_FAMAS (724, FAMAS, AUTO,  ACOG, INC, "AAI_FAMAS", 0.697),
    TAI_FAMAS (725, FAMAS, AUTO,  TACT, INC, "TAI_FAMAS", 0.697),
    LAI_FAMAS (726, FAMAS, AUTO,  LONG, INC, "LAI_FAMAS", 0.697),
    IBI_FAMAS (727, FAMAS, BURST, IRON, INC, "IBI_FAMAS", 0.697),
    ABI_FAMAS (728, FAMAS, BURST, ACOG, INC, "ABI_FAMAS", 0.697),
    TBI_FAMAS (729, FAMAS, BURST, TACT, INC, "TBI_FAMAS", 0.697),
    LBI_FAMAS (730, FAMAS, BURST, LONG, INC, "LBI_FAMAS", 0.697),
    ISI_FAMAS (731, FAMAS, SEMI,  IRON, INC, "ISI_FAMAS", 0.697),
    ASI_FAMAS (732, FAMAS, SEMI,  ACOG, INC, "ASI_FAMAS", 0.697),
    TSI_FAMAS (733, FAMAS, SEMI,  TACT, INC, "TSI_FAMAS", 0.697),
    LSI_FAMAS (734, FAMAS, SEMI,  LONG, INC, "LSI_FAMAS", 0.697),
    
    IA_M4A1  (24,  M4A1, AUTO,  IRON, SUP, "M4A1",     0.704),
    AA_M4A1  (327, M4A1, AUTO,  ACOG, SUP, "AA_M4A1",  0.704),
    TA_M4A1  (328, M4A1, AUTO,  TACT, SUP, "TA_M4A1",  0.704),
    LA_M4A1  (329, M4A1, AUTO,  LONG, SUP, "LA_M4A1",  0.704),
    IB_M4A1  (330, M4A1, BURST, IRON, SUP, "IB_M4A1",  0.704),
    AB_M4A1  (331, M4A1, BURST, ACOG, SUP, "AB_M4A1",  0.704),
    TB_M4A1  (332, M4A1, BURST, TACT, SUP, "TB_M4A1",  0.704),
    LB_M4A1  (333, M4A1, BURST, LONG, SUP, "LB_M4A1",  0.704), 
    IS_M4A1  (334, M4A1, SEMI,  IRON, SUP, "IS_M4A1",  0.704),
    AS_M4A1  (335, M4A1, SEMI,  ACOG, SUP, "AS_M4A1",  0.704),
    TS_M4A1  (336, M4A1, SEMI,  TACT, SUP, "TS_M4A1",  0.704),
    LS_M4A1  (337, M4A1, SEMI,  LONG, SUP, "LS_M4A1",  0.704),
    IAU_M4A1 (338, M4A1, AUTO,  IRON, NA,  "IAU_M4A1", 0.704),
    AAU_M4A1 (339, M4A1, AUTO,  ACOG, NA,  "AAU_M4A1", 0.704),
    TAU_M4A1 (340, M4A1, AUTO,  TACT, NA,  "TAU_M4A1", 0.704),
    LAU_M4A1 (341, M4A1, AUTO,  LONG, NA,  "LAU_M4A1", 0.704),
    IBU_M4A1 (342, M4A1, BURST, IRON, NA,  "IBU_M4A1", 0.704),
    ABU_M4A1 (343, M4A1, BURST, ACOG, NA,  "ABU_M4A1", 0.704),
    TBU_M4A1 (344, M4A1, BURST, TACT, NA,  "TBU_M4A1", 0.704),
    LBU_M4A1 (345, M4A1, BURST, LONG, NA,  "LBU_M4A1", 0.704), 
    ISU_M4A1 (346, M4A1, SEMI,  IRON, NA,  "ISU_M4A1", 0.704),
    ASU_M4A1 (347, M4A1, SEMI,  ACOG, NA,  "ASU_M4A1", 0.704),
    TSU_M4A1 (348, M4A1, SEMI,  TACT, NA,  "TSU_M4A1", 0.704),
    LSU_M4A1 (349, M4A1, SEMI,  LONG, NA,  "LSU_M4A1", 0.704),
    IAR_M4A1 (350, M4A1, AUTO,  IRON, REL, "IAR_M4A1", 0.704),
    AAR_M4A1 (351, M4A1, AUTO,  ACOG, REL, "AAR_M4A1", 0.704),
    TAR_M4A1 (352, M4A1, AUTO,  TACT, REL, "TAR_M4A1", 0.704),
    LAR_M4A1 (353, M4A1, AUTO,  LONG, REL, "LAR_M4A1", 0.704),
    IBR_M4A1 (354, M4A1, BURST, IRON, REL, "IBR_M4A1", 0.704),
    ABR_M4A1 (355, M4A1, BURST, ACOG, REL, "ABR_M4A1", 0.704),
    TBR_M4A1 (356, M4A1, BURST, TACT, REL, "TBR_M4A1", 0.704),
    LBR_M4A1 (357, M4A1, BURST, LONG, REL, "LBR_M4A1", 0.704), 
    ISR_M4A1 (358, M4A1, SEMI,  IRON, REL, "ISR_M4A1", 0.704),
    ASR_M4A1 (359, M4A1, SEMI,  ACOG, REL, "ASR_M4A1", 0.704),
    TSR_M4A1 (360, M4A1, SEMI,  TACT, REL, "TSR_M4A1", 0.704),
    LSR_M4A1 (361, M4A1, SEMI,  LONG, REL, "LSR_M4A1", 0.704),
    IAE_M4A1 (362, M4A1, AUTO,  IRON, EXT, "IAE_M4A1", 0.704),
    AAE_M4A1 (363, M4A1, AUTO,  ACOG, EXT, "AAE_M4A1", 0.704),
    TAE_M4A1 (364, M4A1, AUTO,  TACT, EXT, "TAE_M4A1", 0.704),
    LAE_M4A1 (365, M4A1, AUTO,  LONG, EXT, "LAE_M4A1", 0.704),
    IBE_M4A1 (366, M4A1, BURST, IRON, EXT, "IBE_M4A1", 0.704),
    ABE_M4A1 (367, M4A1, BURST, ACOG, EXT, "ABE_M4A1", 0.704),
    TBE_M4A1 (368, M4A1, BURST, TACT, EXT, "TBE_M4A1", 0.704),
    LBE_M4A1 (369, M4A1, BURST, LONG, EXT, "LBE_M4A1", 0.704), 
    ISE_M4A1 (370, M4A1, SEMI,  IRON, EXT, "ISE_M4A1", 0.704),
    ASE_M4A1 (371, M4A1, SEMI,  ACOG, EXT, "ASE_M4A1", 0.704),
    TSE_M4A1 (372, M4A1, SEMI,  TACT, EXT, "TSE_M4A1", 0.704),
    LSE_M4A1 (373, M4A1, SEMI,  LONG, EXT, "LSE_M4A1", 0.704),
    IAI_M4A1 (735, M4A1, AUTO,  IRON, INC, "IAI_M4A1", 0.704),
    AAI_M4A1 (736, M4A1, AUTO,  ACOG, INC, "AAI_M4A1", 0.704),
    TAI_M4A1 (737, M4A1, AUTO,  TACT, INC, "TAI_M4A1", 0.704),
    LAI_M4A1 (738, M4A1, AUTO,  LONG, INC, "LAI_M4A1", 0.704),
    IBI_M4A1 (739, M4A1, BURST, IRON, INC, "IBI_M4A1", 0.704),
    ABI_M4A1 (740, M4A1, BURST, ACOG, INC, "ABI_M4A1", 0.704),
    TBI_M4A1 (741, M4A1, BURST, TACT, INC, "TBI_M4A1", 0.704),
    LBI_M4A1 (742, M4A1, BURST, LONG, INC, "LBI_M4A1", 0.704), 
    ISI_M4A1 (743, M4A1, SEMI,  IRON, INC, "ISI_M4A1", 0.704),
    ASI_M4A1 (744, M4A1, SEMI,  ACOG, INC, "ASI_M4A1", 0.704),
    TSI_M4A1 (745, M4A1, SEMI,  TACT, INC, "TSI_M4A1", 0.704),
    LSI_M4A1 (746, M4A1, SEMI,  LONG, INC, "LSI_M4A1", 0.704),
   
    IA_SAW  (374, SAW,  AUTO,  IRON, NA,  "SAW",     1.215),
    AA_SAW  (375, SAW,  AUTO,  ACOG, NA,  "AA_SAW",  1.215),
    TA_SAW  (376, SAW,  AUTO,  TACT, NA,  "TA_SAW",  1.215),
    IB_SAW  (377, SAW,  BURST, IRON, NA,  "IB_SAW",  1.215),
    AB_SAW  (378, SAW,  BURST, ACOG, NA,  "AB_SAW",  1.215),
    TB_SAW  (379, SAW,  BURST, TACT, NA,  "TB_SAW",  1.215),
    IS_SAW  (380, SAW,  SEMI,  IRON, NA,  "IS_SAW",  1.215),
    AS_SAW  (381, SAW,  SEMI,  ACOG, NA,  "AS_SAW",  1.215),
    TS_SAW  (382, SAW,  SEMI,  TACT, NA,  "TS_SAW",  1.215),
    IAE_SAW (383, SAW,  AUTO,  IRON, EXT, "IAE_SAW", 1.215),
    AAE_SAW (384, SAW,  AUTO,  ACOG, EXT, "AAE_SAW", 1.215),
    TAE_SAW (385, SAW,  AUTO,  TACT, EXT, "TAE_SAW", 1.215),
    IBE_SAW (386, SAW,  BURST, IRON, EXT, "IBE_SAW", 1.215),
    ABE_SAW (387, SAW,  BURST, ACOG, EXT, "ABE_SAW", 1.215),
    TBE_SAW (388, SAW,  BURST, TACT, EXT, "TBE_SAW", 1.215),
    ISE_SAW (389, SAW,  SEMI,  IRON, EXT, "ISE_SAW", 1.215),
    ASE_SAW (390, SAW,  SEMI,  ACOG, EXT, "ASE_SAW", 1.215),
    TSE_SAW (391, SAW,  SEMI,  TACT, EXT, "TSE_SAW", 1.215),
    IAR_SAW (392, SAW,  AUTO,  IRON, REL, "IAR_SAW", 1.215),
    AAR_SAW (393, SAW,  AUTO,  ACOG, REL, "AAR_SAW", 1.215),
    TAR_SAW (394, SAW,  AUTO,  TACT, REL, "TAR_SAW", 1.215),
    IBR_SAW (395, SAW,  BURST, IRON, REL, "IBR_SAW", 1.215),
    ABR_SAW (396, SAW,  BURST, ACOG, REL, "ABR_SAW", 1.215),
    TBR_SAW (397, SAW,  BURST, TACT, REL, "TBR_SAW", 1.215),
    ISR_SAW (398, SAW,  SEMI,  IRON, REL, "ISR_SAW", 1.215),
    ASR_SAW (399, SAW,  SEMI,  ACOG, REL, "ASR_SAW", 1.215),
    TSR_SAW (400, SAW,  SEMI,  TACT, REL, "TSR_SAW", 1.215),
    IAI_SAW (747, SAW,  AUTO,  IRON, INC, "IAI_SAW", 1.215),
    AAI_SAW (748, SAW,  AUTO,  ACOG, INC, "AAI_SAW", 1.215),
    TAI_SAW (749, SAW,  AUTO,  TACT, INC, "TAI_SAW", 1.215),
    IBI_SAW (750, SAW,  BURST, IRON, INC, "IBI_SAW", 1.215),
    ABI_SAW (751, SAW,  BURST, ACOG, INC, "ABI_SAW", 1.215),
    TBI_SAW (752, SAW,  BURST, TACT, INC, "TBI_SAW", 1.215),
    ISI_SAW (753, SAW,  SEMI,  IRON, INC, "ISI_SAW", 1.215),
    ASI_SAW (754, SAW,  SEMI,  ACOG, INC, "ASI_SAW", 1.215),
    TSI_SAW (755, SAW,  SEMI,  TACT, INC, "TSI_SAW", 1.215),
    
    /* SHOTGUNS ***************************************************************/
    SI_Olympia  (26,  OLYMPIA, SING, IRON, NA,  "Olympia",     2.43),
    SA_Olympia  (401, OLYMPIA, SING, ACOG, NA,  "SA_Olympia ", 2.43),
    SIS_Olympia (402, OLYMPIA, SING, IRON, SO,  "SIS_Olympia", 2.43),
    SAS_Olympia (403, OLYMPIA, SING, ACOG, SO,  "SAS_Olympia", 2.43),
    SIR_Olympia (404, OLYMPIA, SING, IRON, REL, "SIR_Olympia", 2.43),
    SAR_Olympia (405, OLYMPIA, SING, ACOG, REL, "SAR_Olympia", 2.43),
    SII_Olympia (756, OLYMPIA, SING, IRON, INC, "SII_Olympia", 2.43),
    SAI_Olympia (757, OLYMPIA, SING, ACOG, INC, "SAI_Olympia", 2.43),
    
    I_AA12  (27,  AA12, SEMI, IRON, NA,  "AA12",    2.0),
    A_AA12  (406, AA12, SEMI, ACOG, NA,  "A_AA12 ", 2.0),
    IS_AA12 (407, AA12, SEMI, IRON, SO,  "IS_AA12", 2.0),
    AS_AA12 (408, AA12, SEMI, ACOG, SO,  "AS_AA12", 2.0),
    IR_AA12 (409, AA12, SEMI, IRON, REL, "IR_AA12", 2.0),
    AR_AA12 (410, AA12, SEMI, ACOG, REL, "AR_AA12", 2.0),
    IE_AA12 (411, AA12, SEMI, IRON, EXT, "IE_AA12", 2.0),
    AE_AA12 (412, AA12, SEMI, ACOG, EXT, "AE_AA12", 2.0),
    II_AA12 (758, AA12, SEMI, IRON, INC, "II_AA12", 2.0),
    AI_AA12 (759, AA12, SEMI, ACOG, INC, "AI_AA12", 2.0),
    
    I_M3  (413, M3, PUMP, IRON, NA,  "M3",    1.88),
    A_M3  (414, M3, PUMP, ACOG, NA,  "A_M3 ", 1.88),
    IS_M3 (415, M3, PUMP, IRON, SO,  "IS_M3", 1.88),
    AS_M3 (416, M3, PUMP, ACOG, SO,  "AS_M3", 1.88),
    IR_M3 (417, M3, PUMP, IRON, REL, "IR_M3", 1.88),
    AR_M3 (418, M3, PUMP, ACOG, REL, "AR_M3", 1.88),
    IE_M3 (419, M3, PUMP, IRON, EXT, "IE_M3", 1.88),
    AE_M3 (420, M3, PUMP, ACOG, EXT, "AE_M3", 1.88),
    II_M3 (760, M3, PUMP, IRON, INC, "II_M3", 1.88),
    AI_M3 (761, M3, PUMP, ACOG, INC, "AI_M3", 1.88),
    
    I_XM1014  (29,  XM1014, SEMI, IRON, NA,  "XM1014",    2.2),
    A_XM1014  (421, XM1014, SEMI, ACOG, NA,  "A_XM1014 ", 2.2),
    IS_XM1014 (422, XM1014, SEMI, IRON, SO,  "IS_XM1014", 2.2),
    AS_XM1014 (423, XM1014, SEMI, ACOG, SO,  "AS_XM1014", 2.2),
    IR_XM1014 (424, XM1014, SEMI, IRON, REL, "IR_XM1014", 2.2),
    AR_XM1014 (425, XM1014, SEMI, ACOG, REL, "AR_XM1014", 2.2),
    IE_XM1014 (426, XM1014, SEMI, IRON, EXT, "IE_XM1014", 2.2),
    AE_XM1014 (427, XM1014, SEMI, ACOG, EXT, "AE_XM1014", 2.2),
    II_XM1014 (762, XM1014, SEMI, IRON, INC, "II_XM1014", 2.2),
    AI_XM1014 (763, XM1014, SEMI, ACOG, INC, "AI_XM1014", 2.2);
    
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
        return (GunUtils.hasScope(this)) ? 
                getCSBulletSpread() : initialBulletSpread * 0.85;
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
        if (durability == 0)
            return Condition.BROKEN.getEnumValue();
        
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
