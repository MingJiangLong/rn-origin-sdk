import { useEffect } from 'react';
import { Text, View, StyleSheet, PermissionsAndroid } from 'react-native';


export default function App() {




  const test = async () => {



  }
  useEffect(() => {
    test()
  }, [])
  return (
    <View style={styles.container}>
      <Text>Result: { }</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
