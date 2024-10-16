import React, { useState, useRef } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Animated,
} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';

interface Reaction {
  name: string;
  label: string;
  color: string;
  icon: string;
}

const reactions: Reaction[] = [
  { name: 'thumbs-up', label: 'Like', color: '#4267B2', icon: 'thumbs-up' },
  { name: 'heart', label: 'Love', color: '#E74C3C', icon: 'heart' },
  { name: 'haha', label: 'Haha', color: '#F1C40F', icon: '😂' },
  { name: 'wow', label: 'Wow', color: '#F39C12', icon: '😮' },
  { name: 'sad', label: 'Sad', color: '#3498DB', icon: '😢' },
  { name: 'angry', label: 'Angry', color: '#E74C3C', icon: '😠' },
];

const LikeButton = () => {
  const [showReactions, setShowReactions] = useState(false);
  const [selectedReaction, setSelectedReaction] = useState<Reaction | null>(null);
  const [likeCount, setLikeCount] = useState(7);
  const scaleAnim = useRef(new Animated.Value(0)).current;

  const handleLongPress = () => {
    setShowReactions(true);
    Animated.spring(scaleAnim, {
      toValue: 1,
      useNativeDriver: true,
    }).start();
  };

  const handleSelectReaction = (reaction: Reaction) => {
    setSelectedReaction(reaction);
    setLikeCount(likeCount + 1);
    setShowReactions(false);
    Animated.timing(scaleAnim, {
      toValue: 0,
      duration: 200,
      useNativeDriver: true,
    }).start();
  };

  const handlePress = () => {
    if (selectedReaction) {
      setSelectedReaction(null);
      setLikeCount(likeCount > 0 ? likeCount - 1 : 0);
    } else {
      handleSelectReaction(reactions[0]);
    }
  };

  const renderReactionIcon = (reaction: Reaction, size: number) => {
    if (reaction.name === 'thumbs-up' || reaction.name === 'heart') {
      return <Icon name={reaction.icon} size={size} color={reaction.color} />;
    } else {
      return <Text style={[styles.emojiIcon, { fontSize: size, color: reaction.color }]}>{reaction.icon}</Text>;
    }
  };

  return (
    <View style={styles.container}>
      {/* Display like count conditionally */}
      {likeCount > 0 ? (
        <View style={styles.likeCountContainer}>
          <Text style={styles.likeCountText}>{likeCount} lượt tương tác</Text>
        </View>
      ) : (
        <Text style={styles.likeCountText}></Text>
      )}

      <Animated.View style={[
        styles.reactionBar,
        {
          transform: [{ scale: scaleAnim }],
          opacity: scaleAnim,
        }
      ]}>
        {showReactions && reactions.map((reaction, index) => (
          <TouchableOpacity
            key={index}
            style={styles.reactionItem}
            onPress={() => handleSelectReaction(reaction)}
          >
            {renderReactionIcon(reaction, 20)}
          </TouchableOpacity>
        ))}
      </Animated.View>
      
      <TouchableOpacity
        style={styles.likeButton}
        onPress={handlePress}
        onLongPress={handleLongPress}
      >
        {selectedReaction ? renderReactionIcon(selectedReaction, 24) : <Icon name="thumbs-up" size={24} color="#000" />}
        <Text style={[styles.likeText, selectedReaction && { color: selectedReaction.color }]}>
          {selectedReaction?.label || 'Like'}
        </Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'relative',
    marginVertical: 10,
  },
  likeCountContainer: {
    left: '-10%',
    alignItems: 'center',
  },
  likeCountText: {
    color: 'black',
    fontSize: 12,
  },
  reactionBar: {
    position: 'absolute',
    bottom: 40,
    left: 0,
    flexDirection: 'row',
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 5,
    elevation: 5,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
  },
  reactionItem: {
    padding: 5,
    marginHorizontal: 2,
    justifyContent: 'center',
    alignItems: 'center',
    width: 36,
    height: 36,
  },
  likeButton: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 5,
  },
  likeText: {
    marginLeft: 5,
  },
  emojiIcon: {
    textAlign: 'center',
    lineHeight: 24,
  },
});

export default LikeButton;
