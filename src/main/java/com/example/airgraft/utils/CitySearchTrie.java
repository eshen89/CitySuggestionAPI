package com.example.airgraft.utils;

import com.example.airgraft.model.pojo.City;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CitySearchTrie {

  private final CityTrieNode root;

  public CitySearchTrie() {
    root = new CityTrieNode();
  }

  public void insert(City city) {
    String cityName = city.getName().toLowerCase(Locale.ROOT);
    int len = city.getName().length();
    CityTrieNode ptr = root;
    for(var i = 0; i < len; i++) {
      var charAt = cityName.charAt(i);
      Optional<CityTrieNode> found = ptr.children.stream()
          .filter(cityTrieNode -> cityTrieNode.c == charAt)
          .findFirst();

      if (found.isPresent()) {
        ptr = found.get();
      } else {
        var newChild = new CityTrieNode(charAt, ptr.index);
        ptr.children.add(newChild);
        ptr = newChild;
      }
    }
    ptr.geoIds.add(city.getGeoNameId());
  }

  public boolean isEmpty() {
    return root.children.isEmpty();
  }

  public List<Integer> suggest(String cityNamePrefix) {
    CityTrieNode parent = searchParent(cityNamePrefix.toLowerCase(Locale.ROOT));
    return dfs(parent);
  }

  private List<Integer> dfs(CityTrieNode root) {
    List<Integer> geoIds = new LinkedList<>();

    if(root == null) {
      return geoIds;
    }

    if (!root.geoIds.isEmpty()) {
      geoIds.addAll(root.geoIds);
    }
    if(!root.children.isEmpty()) {
      for (CityTrieNode node: root.children) {
        geoIds.addAll(dfs(node));
      }
    }

    return geoIds;
  }

  private CityTrieNode searchParent(String cityNamePrefix) {
    int len = cityNamePrefix.length();
    CityTrieNode ptr = root;
    for(var i = 0; i < len; i++) {
      var charAt = cityNamePrefix.charAt(i);
      Optional<CityTrieNode> found = ptr.children.stream()
          .filter(cityTrieNode -> cityTrieNode.c == charAt)
          .findFirst();

      if (found.isPresent()) {
        ptr = found.get();
      } else {
        break;
      }
    }
    //There's no parent if the prefix has more characters than what the trie can search for.
    //Example: search term is "NotExist", if there's no more node after 'E' then we return null.
    if (ptr.index < len) {
      return null;
    }

    return ptr;
  }

  private static class CityTrieNode {
    int index;
    char c;
    List<CityTrieNode> children;
    List<Integer> geoIds;
    CityTrieNode() {
      children = new LinkedList<>();
      geoIds = new LinkedList<>();
    }
    CityTrieNode(char c, int index) {
      this.c = c;
      this.index = index + 1;
      children = new LinkedList<>();
      geoIds = new LinkedList<>();
    }
  }

}
