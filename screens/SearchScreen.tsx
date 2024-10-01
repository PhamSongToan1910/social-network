import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  TextInput,
  FlatList,
  TouchableOpacity,
  StyleSheet,
  SafeAreaView,
  Platform,
  StatusBar,
  Alert,
} from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';

const SearchScreen = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [searchHistory, setSearchHistory] = useState<string[]>([]);

  useEffect(() => {
    // Giả lập việc lấy lịch sử tìm kiếm từ bộ nhớ local
    setSearchHistory(['React Native', 'JavaScript', 'TypeScript', 'Mobile Development']);
  }, []);

  const handleSearch = () => {
    if (searchQuery.trim()) {
      setSearchHistory(prevHistory => [searchQuery, ...prevHistory.filter(item => item !== searchQuery)]);
      console.log('Searching for:', searchQuery);
      setSearchQuery('');
    }
  };

  const handleDeleteHistoryItem = (item: string) => {
    setSearchHistory(prevHistory => prevHistory.filter(historyItem => historyItem !== item));
  };

  const handleClearAllHistory = () => {
    Alert.alert(
      "Xóa lịch sử tìm kiếm",
      "Bạn có chắc muốn xóa tất cả lịch sử tìm kiếm?",
      [
        {
          text: "Hủy",
          style: "cancel"
        },
        { 
          text: "Xóa", 
          onPress: () => setSearchHistory([])
        }
      ]
    );
  };

  const renderHistoryItem = ({ item }: { item: string }) => (
    <View style={styles.historyItem}>
      <TouchableOpacity
        style={styles.historyTextContainer}
        onPress={() => setSearchQuery(item)}
      >
        <Icon name="time-outline" size={20} color="#999" />
        <Text style={styles.historyText}>{item}</Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={() => handleDeleteHistoryItem(item)}>
        <Icon name="close" size={20} color="#999" />
      </TouchableOpacity>
    </View>
  );

  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.container}>
        <View style={styles.searchContainer}>
          <Icon name="search" size={20} color="#999" style={styles.searchIcon} />
          <TextInput
            style={styles.searchInput}
            placeholder="Tìm kiếm"
            placeholderTextColor="#999"
            value={searchQuery}
            onChangeText={setSearchQuery}
            onSubmitEditing={handleSearch}
          />
          {searchQuery.length > 0 && (
            <TouchableOpacity onPress={() => setSearchQuery('')} style={styles.clearButton}>
              <Icon name="close-circle" size={20} color="#999" />
            </TouchableOpacity>
          )}
        </View>
        
        {searchHistory.length > 0 && (
          <View style={styles.historyContainer}>
            <View style={styles.historyHeader}>
              <Text style={styles.historyTitle}>Tìm kiếm gần đây</Text>
              <TouchableOpacity onPress={handleClearAllHistory}>
                <Text style={styles.clearAllText}>Xóa tất cả</Text>
              </TouchableOpacity>
            </View>
            <FlatList
              data={searchHistory}
              renderItem={renderHistoryItem}
              keyExtractor={(item, index) => index.toString()}
            />
          </View>
        )}
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#fff',
    paddingTop: Platform.OS === 'android' ? StatusBar.currentHeight : 0,
  },
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
  searchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#f2f2f2',
    borderRadius: 10,
    margin: 10,
    paddingHorizontal: 10,
  },
  searchIcon: {
    marginRight: 10,
  },
  searchInput: {
    flex: 1,
    height: 40,
    fontSize: 16,
    color: '#000',
  },
  clearButton: {
    padding: 5,
  },
  historyContainer: {
    flex: 1,
    padding: 10,
  },
  historyHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 10,
  },
  historyTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#000',
  },
  clearAllText: {
    color: '#007AFF',
    fontSize: 14,
  },
  historyItem: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingVertical: 12,
    borderBottomWidth: 1,
    borderBottomColor: '#f0f0f0',
  },
  historyTextContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    flex: 1,
  },
  historyText: {
    marginLeft: 10,
    fontSize: 16,
    color: '#000',
  },
});

export default SearchScreen;