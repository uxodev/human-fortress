package io.github.uxodev.file;

public class FileError {
    public enum ErrorId {
        TOML_MISSING, TOML_INVALID, TOML_EMPTY,
        SHAPE_NAME, SHAPE_CLASS_NO_MATCHING_CLASS, SHAPE_NUMERIC, SHAPE_CONTAINER_MISSING_CONTAINS,
        PATH_TOKEN_NO_MATCHING_TOKEN, CONTAIN_TOKEN_NO_MATCHING_ITEM, CONTAIN_TOKEN_NO_MATCHING_CONTAINER,
        MATERIAL_NAME, MATERIAL_NUMBER,
        SOURCE_NAME, SOURCE_MISSING_ENTRY, SOURCE_NUMERIC,
        TEMPLATE_NAME, TEMPLATE_MISSING_ENTRY,
        FORMULA_NAME, FORMULA_AREA, FORMULA_AREA_NO_MATCHING_AREA,
        FORMULA_MISSING_OUTPUT,
        FORMULA_MISSING_INPUT,

        AREA_NO_MATCHING_FORMULA_AREA,
    }

    public static boolean isNull(Object testObject, ErrorId errorId, String errorLocation) {
        if (testObject == null) {
            throwError(errorId, errorLocation, null);
            return true;
        }
        return false;
    }

    public static boolean isNull(Object testObject, ErrorId errorId, String errorLocation, String errorCauser) {
        if (testObject == null) {
            throwError(errorId, errorLocation, errorCauser);
            return true;
        }
        return false;
    }

    public static void throwError(ErrorId errorId, String errorLocation) {
        FileError.throwError(errorId, errorLocation, null);
    }

    public static void throwError(ErrorId errorId, String errorLocation, String errorCauser) {
        switch (errorId) {
            case TOML_MISSING:
                System.err.println(errorLocation + " does not exist, did not parse file");
                break;
            case TOML_INVALID:
                System.err.println(errorLocation + " is not valid TOML, did not parse file");
                break;
            case TOML_EMPTY:
                System.err.println(errorLocation + " is empty, did not parse file");
                break;

            case SHAPE_NAME:
                System.err.println("missing shape.name in shape: " + errorLocation);
                break;
            case SHAPE_CLASS_NO_MATCHING_CLASS:
                System.err.println("no matching class for shape.class: \"" + errorCauser + "\" in shape: " + errorLocation);
                break;
            case SHAPE_NUMERIC:
                System.err.println("a field is not numeric in shape: " + errorLocation);
                break;
            case SHAPE_CONTAINER_MISSING_CONTAINS:
                System.err.println("missing shape.contains in container shape: " + errorLocation);
                break;
            case PATH_TOKEN_NO_MATCHING_TOKEN: // notice, does not skip anything
                System.err.println("no matching path token for shape.path_token: \"" + errorCauser + "\" in shape: " + errorLocation);
                break;
            case CONTAIN_TOKEN_NO_MATCHING_ITEM: // notice
                System.err.println("no matching item for shape.contains: \"" + errorCauser + "\" in container shape: " + errorLocation);
                break;
            case CONTAIN_TOKEN_NO_MATCHING_CONTAINER: // notice
                System.err.println("no matching container shape for shape.containable: \"" + errorLocation + "\"");
                break;

            case MATERIAL_NAME:
                System.err.println("missing material.name for material: " + errorLocation);
                break;
            case MATERIAL_NUMBER:
                System.err.println("a field is not an integer in material: " + errorLocation);
                break;

            case SOURCE_NAME:
                System.err.println("missing source.name for source: " + errorLocation);
                break;
            case SOURCE_MISSING_ENTRY:
                System.err.println("missing entry in source: " + errorLocation);
                break;
            case SOURCE_NUMERIC:
                System.err.println("a field is not numeric in source: " + errorLocation);
                break;

            case TEMPLATE_NAME:
                System.err.println("missing template.name for template: " + errorLocation);
                break;
            case TEMPLATE_MISSING_ENTRY:
                System.err.println("missing entry in template: " + errorLocation);
                break;

            case FORMULA_NAME:
                System.err.println("missing formula.name for formula: " + errorLocation);
                break;
            case FORMULA_AREA:
                System.err.println("missing formula.area for formula: " + errorLocation);
                break;
            case FORMULA_AREA_NO_MATCHING_AREA:
                System.err.println("no matching area for formula.area: \"" + errorCauser + "\" in formula: \"" + errorLocation + "\"");
                break;
            case FORMULA_MISSING_OUTPUT:
                System.err.println("missing formula.output for formula: " + errorLocation);
                break;
            case FORMULA_MISSING_INPUT:
                System.err.println("missing formula.input for formula: " + errorLocation);
                break;

            case AREA_NO_MATCHING_FORMULA_AREA:
                break;

            default:
                System.err.println("UNKNOWN ERROR " + errorLocation + " " + errorCauser);
                break;
        }
    }
}
