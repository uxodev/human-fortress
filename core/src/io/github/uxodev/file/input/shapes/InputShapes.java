package io.github.uxodev.file.input.shapes;

import io.github.uxodev.file.FileError;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

import static io.github.uxodev.file.FileError.ErrorId.CONTAIN_TOKEN_NO_MATCHING_ITEM;
import static io.github.uxodev.file.FileError.ErrorId.PATH_TOKEN_NO_MATCHING_TOKEN;

public class InputShapes {
    public static void parseShapes() {
        InputShapesDynamic.parseDynamicShapes("data\\widget\\shape\\dynamic\\commodity shapes.toml");
        InputShapesDynamic.parseDynamicShapes("data\\widget\\shape\\dynamic\\item shapes.toml");
        InputShapesDynamic.parseDynamicShapes("data\\widget\\shape\\dynamic\\prefab shapes.toml");
        InputShapesDynamic.parseDynamicShapes("data\\widget\\shape\\dynamic\\part shapes.toml");

        InputShapesStable.addHardcodedTerrain();
        InputShapesStable.parseStableShapes("data\\widget\\shape\\stable\\terrain shapes.toml");
        InputShapesStable.parseStableShapes("data\\widget\\shape\\stable\\furniture shapes.toml");

        InputShapesContainer.parseContainerShapes("data\\widget\\shape\\container\\container shapes.toml");
    }

    static HashSet<UseToken> registerUseTokens(List<String> use_tokens) {
        HashSet<UseToken> useTokens = new HashSet<>();
        for (String use_token : use_tokens) {
            ShapeData.USE_TOKENS.putIfAbsent(use_token, new UseToken(use_token));
            useTokens.add(ShapeData.USE_TOKENS.get(use_token));
        }
        return useTokens;
    }

    static EnumSet<PathToken> registerPathTokens(List<String> path_tokens, String nameShape) {
        EnumSet<PathToken> pathTokens = EnumSet.noneOf(PathToken.class);
        for (int i = 0; i < path_tokens.size(); i++) {
            String path_token = path_tokens.get(i);
            if (path_token.equals("blocks all")) {
                Collections.addAll(path_tokens, "blocks light", "blocks gas", "blocks liquid", "blocks solid", "blocks unit");
                continue;
            }
            String enumName = path_token.toUpperCase().replaceAll(" ", "_");
            if (PathToken.map.contains(enumName)) {
                pathTokens.add(PathToken.valueOf(enumName));
            } else {
                FileError.throwError(PATH_TOKEN_NO_MATCHING_TOKEN, nameShape, path_token);
            }
        }
        return pathTokens;
    }

    static ContainToken registerContainableToken(String containable) {
        return ShapeData.CONTAIN_TOKENS.putIfAbsent(containable, new ContainToken(containable));
    }

    static HashSet<ContainToken> registerContainsTokens(List<String> contains, HashSet<String> containingShapeTokens, String nameShape) {
        HashSet<ContainToken> containsTokens = new HashSet<>();
        for (String contain : contains) {
            if (FileError.isNull(ShapeData.CONTAIN_TOKENS.get(contain), CONTAIN_TOKEN_NO_MATCHING_ITEM, nameShape, contain))
                continue;
            containsTokens.add(ShapeData.CONTAIN_TOKENS.get(contain));
            containingShapeTokens.add(contain);
        }
        return containsTokens;
    }
}
