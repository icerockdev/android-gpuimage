package jp.co.cyberagent.android.gpuimage;

public final class BlendHelperFunctions {

    public static String hslColorSpaceHelperFunctions() {
        return "mediump vec3 RGBToHSL(mediump vec3 color) {\n" +
                "   mediump vec3 hsl; // init to 0 to avoid warnings ? (and reverse if + remove first part)\n" +
                "   \n" +
                "   mediump float fmin = min(min(color.r, color.g), color.b);    //Min. value of RGB\n" +
                "   mediump float fmax = max(max(color.r, color.g), color.b);    //Max. value of RGB\n" +
                "   mediump float delta = fmax - fmin;             //Delta RGB value\n" +
                "   \n" +
                "   hsl.z = (fmax + fmin) / 2.0; // Luminance\n" +
                "   \n" +
                "   if (delta == 0.0)\t\t//This is a gray, no chroma...\n" +
                "       {\n" +
                "     hsl.x = 0.0;\t// Hue\n" +
                "     hsl.y = 0.0;\t// Saturation\n" +
                "       }\n" +
                "   else                                    //Chromatic data...\n" +
                "       {\n" +
                "     if (hsl.z < 0.5)\n" +
                "       hsl.y = delta / (fmax + fmin); // Saturation\n" +
                "     else\n" +
                "       hsl.y = delta / (2.0 - fmax - fmin); // Saturation\n" +
                "     \n" +
                "     mediump float deltaR = (((fmax - color.r) / 6.0) + (delta / 2.0)) / delta;\n" +
                "     mediump float deltaG = (((fmax - color.g) / 6.0) + (delta / 2.0)) / delta;\n" +
                "     mediump float deltaB = (((fmax - color.b) / 6.0) + (delta / 2.0)) / delta;\n" +
                "     \n" +
                "     if (color.r == fmax )\n" +
                "       hsl.x = deltaB - deltaG; // Hue\n" +
                "     else if (color.g == fmax)\n" +
                "       hsl.x = (1.0 / 3.0) + deltaR - deltaB; // Hue\n" +
                "     else if (color.b == fmax)\n" +
                "       hsl.x = (2.0 / 3.0) + deltaG - deltaR; // Hue\n" +
                "     \n" +
                "     if (hsl.x < 0.0)\n" +
                "       hsl.x += 1.0; // Hue\n" +
                "     else if (hsl.x > 1.0)\n" +
                "       hsl.x -= 1.0; // Hue\n" +
                "       }\n" +
                "   \n" +
                "   return hsl;\n" +
                " }\n" +
                " \n" +
                " mediump float HueToRGB(mediump float f1, mediump float f2, mediump float hue) {\n" +
                "   if (hue < 0.0)\n" +
                "     hue += 1.0;\n" +
                "   else if (hue > 1.0)\n" +
                "     hue -= 1.0;\n" +
                "   mediump float res;\n" +
                "   if ((6.0 * hue) < 1.0)\n" +
                "     res = f1 + (f2 - f1) * 6.0 * hue;\n" +
                "   else if ((2.0 * hue) < 1.0)\n" +
                "     res = f2;\n" +
                "   else if ((3.0 * hue) < 2.0)\n" +
                "     res = f1 + (f2 - f1) * ((2.0 / 3.0) - hue) * 6.0;\n" +
                "   else\n" +
                "     res = f1;\n" +
                "   return res;\n" +
                " }\n" +
                " \n" +
                " mediump vec3 HSLToRGB(mediump vec3 hsl) {\n" +
                "   mediump vec3 rgb;\n" +
                "   \n" +
                "   if (hsl.y == 0.0)\n" +
                "     rgb = vec3(hsl.z); // Luminance\n" +
                "   else\n" +
                "       {\n" +
                "     mediump float f2;\n" +
                "     \n" +
                "     if (hsl.z < 0.5)\n" +
                "       f2 = hsl.z * (1.0 + hsl.y);\n" +
                "     else\n" +
                "       f2 = (hsl.z + hsl.y) - (hsl.y * hsl.z);\n" +
                "     \n" +
                "     mediump float f1 = 2.0 * hsl.z - f2;\n" +
                "     \n" +
                "     rgb.r = HueToRGB(f1, f2, hsl.x + (1.0/3.0));\n" +
                "     rgb.g = HueToRGB(f1, f2, hsl.x);\n" +
                "     rgb.b= HueToRGB(f1, f2, hsl.x - (1.0/3.0));\n" +
                "       }\n" +
                "   \n" +
                "   return rgb;\n" +
                " }";
    }

    public static String overlayF() {
        return " mediump float overlayF(mediump float base, mediump float blend) {\n" +
                "   return (base < 0.5 ? (2.0 * base * blend) : (1.0 - 2.0 * (1.0 - base) * (1.0 - blend)));\n" +
                " }";
    }

    public static String linearBurnF() {
        return " mediump float linearBurnF(mediump float base, mediump float blend) {\n" +
                "   return max(base + blend - 1.0, 0.0);\n" +
                " }";
    }

    public static String linearDodgeF() {
        return " mediump float linearDodgeF(mediump float base, mediump float blend) {\n" +
                "   return min(base + blend, 1.0);\n" +
                " }";
    }

    public static String colorBurnF() {
        return " mediump float colorBurnF(mediump float base, mediump float blend) {\n" +
                "   return ((blend == 0.0) ? blend : max((1.0 - ((1.0 - base) / blend)), 0.0));\n" +
                " }";
    }

    public static String colorDodgeF() {
        return " mediump float colorDodgeF(mediump float base, mediump float blend) {\n" +
                "   return ((blend == 1.0) ? blend : min(base / (1.0 - blend), 1.0));\n" +
                " }";
    }

    public static String divideF() {
        return "   mediump float divideF(mediump float base, mediump float blend) {\n" +
                "     return (blend == 0.0) ? 1.0 : base/blend;\n" +
                "   }";
    }

    public static String darkenF() {
        return " mediump float darkenF(mediump float base, mediump float blend) {\n" +
                "   return min(base, blend);\n" +
                " }";
    }

    public static String hardMixF() {
        return String.format("%s\n%s",
                vividLightF(),
                "   mediump float hardMixF(mediump float base, mediump float blend) {\n" +
                        "     return ((vividLightF(base, blend) < 0.5) ? 0.0 : 1.0);\n" +
                        "   }");
    }

    public static String lightenF() {
        return " mediump float lightenF(mediump float base, mediump float blend) {\n" +
                "   return max(base, blend);\n" +
                " }";
    }

    public static String reflectF() {
        return " mediump float reflectF(mediump float base, mediump float blend) {\n" +
                "   return ((blend == 1.0) ? blend : min(base * base / (1.0 - blend), 1.0));\n" +
                " }";
    }

    public static String vividLightF() {
        return String.format("%s\n%s\n%s",
                colorBurnF(),
                colorDodgeF(),
                " mediump float vividLightF(mediump float base, mediump float blend) {\n" +
                        "   return ((blend < 0.5) ?\n" +
                        "           colorBurnF(base, (2.0 * blend)) :\n" +
                        "           colorDodgeF(base, (2.0 * (blend - 0.5))));\n" +
                        " }");
    }

    public static String linearLightF() {
        return String.format("%s\n%s\n%s",
                linearBurnF(),
                linearDodgeF(),
                "   mediump float linearLightF(mediump float base, mediump float blend) {\n" +
                        "     return (blend < 0.5 ?\n" +
                        "             linearBurnF(base, (2.0 * blend)) :\n" +
                        "             linearDodgeF(base, (2.0 * (blend - 0.5))));\n" +
                        "   }");
    }

    public static String pinLightF() {
        return String.format("%s\n%s\n%s",
                darkenF(),
                lightenF(),
                "   mediump float pinLightF(mediump float base, mediump float blend) {\n" +
                        "     return ((blend < 0.5) ?\n" +
                        "             darkenF(base, (2.0 * blend)) :\n" +
                        "             lightenF(base, (2.0 *(blend - 0.5))));\n" +
                        "   }");
    }

    public static String softLightF() {
        return "   mediump float softLightF(mediump float base, mediump float blend) {\n" +
                "     return ((blend < 0.5) ?\n" +
                "             (2.0 * base * blend + base * base * (1.0 - 2.0 * blend)) :\n" +
                "             (sqrt(base) * (2.0 * blend - 1.0) + 2.0 * base * (1.0 - blend)));\n" +
                "   }";
    }
}


