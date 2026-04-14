import { useEffect } from 'react';
import { Text, View, StyleSheet, PermissionsAndroid } from 'react-native';
import { multiply, getAddress, getApplicationList } from 'react-native-xunmo-sdk';

const result = multiply(3, 7);

export default function App() {




  const test = async () => {
    // const status = await PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION)
    // console.log(status);

    // const res = await getAddress(35.6895, 139.6923)
    // console.log("????", res);

    const res2 = await getApplicationList("com.flexiprestamo.cre.FlexiDb", "createJson",[ null, false])
    console.log("res2res2", res2);


  }
  useEffect(() => {
    test()
  }, [])
  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
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
