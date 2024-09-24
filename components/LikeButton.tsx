// LikeButton.tsx
import React, { useState, useRef } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Animated,
  Easing,
} from 'react-native';
import { GestureHandlerRootView, LongPressGestureHandler } from 'react-native-gesture-handler';
import Icon from 'react-native-vector-icons/FontAwesome';

// Định nghĩa kiểu cho đối tượng phản ứng
interface Reaction {
  name: string;
  label: string;
  color: string;
}

// Dữ liệu cho các biểu tượng cảm xúc
const reactions: Reaction[] = [
  { name: 'thumbs-up', label: 'Like', color: '#4267B2' },
  { name: 'heart', label: 'Love', color: '#E74C3C' },
  { name: 'smile-o', label: 'HaHa', color: '#F1C40F' },
  { name: 'surprise', label: 'Wow', color: '#F39C12' },
  { name: 'frown-o', label: 'Sad', color: '#3498DB' },
  { name: 'angry', label: 'Angry', color: '#E74C3C' },
];

const LikeButton = () => {
  const [showReactions, setShowReactions] = useState(false);
  const [selectedReaction, setSelectedReaction] = useState<Reaction | null>(null); // Định nghĩa kiểu cho selectedReaction
  const scaleAnim = useRef(new Animated.Value(0)).current;

  // Animation khi hiển thị và ẩn thanh cảm xúc
  const showReactionBar = () => {
    setShowReactions(true);
    Animated.timing(scaleAnim, {
      toValue: 1,
      duration: 300,
      easing: Easing.out(Easing.ease),
      useNativeDriver: true,
    }).start();
  };

  const hideReactionBar = () => {
    Animated.timing(scaleAnim, {
      toValue: 0,
      duration: 200,
      easing: Easing.in(Easing.ease),
      useNativeDriver: true,
    }).start(() => setShowReactions(false));
  };

  const handleSelectReaction = (reaction: Reaction) => {
    setSelectedReaction(reaction);
    hideReactionBar();
  };

  return (
    <GestureHandlerRootView style={styles.container}>
      {/* Thanh cảm xúc */}
      {showReactions && (
        <Animated.View style={[styles.reactionBar, { transform: [{ scale: scaleAnim }] }]}>
          {reactions.map((reaction, index) => (
            <TouchableOpacity
              key={index}
              style={styles.reactionItem}
              onPress={() => handleSelectReaction(reaction)}
            >
              <Icon name={reaction.name} size={24} color={reaction.color} />
            </TouchableOpacity>
          ))}
        </Animated.View>
      )}

      {/* Nút Like */}
      <LongPressGestureHandler
        minDurationMs={300}
        onActivated={showReactionBar}
        onEnded={hideReactionBar}
      >
        <TouchableOpacity style={styles.actions}>
          <Icon
            name={selectedReaction ? selectedReaction.name : 'thumbs-up'}
            size={24}
            style={styles.icon}
            color={selectedReaction ? selectedReaction.color : '#000'}
          />
          <Text style={styles.likeText}>{selectedReaction ? selectedReaction.label : 'Like'}</Text>
        </TouchableOpacity>
      </LongPressGestureHandler>
    </GestureHandlerRootView>
  );
};

const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 20,
  },
  actions: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#f0f0f0',
    padding: 10,
    borderRadius: 25,
  },
  icon: {
    marginRight: 5,
  },
  likeText: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  reactionBar: {
    position: 'absolute',
    bottom: 60,
    flexDirection: 'row',
    backgroundColor: '#fff',
    borderRadius: 50,
    paddingHorizontal: 10,
    paddingVertical: 5,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
    elevation: 5,
  },
  reactionItem: {
    marginHorizontal: 10,
  },
});

export default LikeButton;
