/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.pantheon.ethereum.mainnet.precompiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import tech.pegasys.pantheon.ethereum.core.Gas;
import tech.pegasys.pantheon.ethereum.mainnet.ConstantinopleFixGasCalculator;
import tech.pegasys.pantheon.ethereum.vm.MessageFrame;
import tech.pegasys.pantheon.util.bytes.BytesValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BLAKE2BFPrecompileContractTest {
  private final BLAKE2BFPrecompileContract contract =
      new BLAKE2BFPrecompileContract(new ConstantinopleFixGasCalculator());

  public BLAKE2BFPrecompileContractTest() {}

  private MessageFrame messageFrame = mock(MessageFrame.class);

  // Test vectors from
  // https://github.com/keep-network/go-ethereum/blob/f-precompile/core/vm/contracts_test.go#L350
  @Parameterized.Parameters
  public static Object[][] parameters() {
    return new Object[][] {
      {
        "000000016a09e667f2bd8948bb67ae8584caa73b3c6ef372fe94f82ba54ff53a5f1d36f1510e527fade682d19b05688c2b3e6c1f1f83d9abfb41bd6b5be0cd19137e217907060504030201000f0e0d0c0b0a090817161514131211101f1e1d1c1b1a191827262524232221202f2e2d2c2b2a292837363534333231303f3e3d3c3b3a3938000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000000000001",
        "62305ad4d48dade8269ef60a1bcb8b7bef6e479e643b5ac1f8017e6422ce89fb62d09ecaa81d095e855540dcbc07bd0feb3d4f5e5e50541260ed930f027cfd8d",
        1
      },
      {
        "000000016a09e667f2bd8948bb67ae8584caa73b3c6ef372fe94f82ba54ff53a5f1d36f1510e527fade682d19b05688c2b3e6c1f1f83d9abfb41bd6b5be0cd19137e217907060504030201000f0e0d0c0b0a090817161514131211101f1e1d1c1b1a191827262524232221202f2e2d2c2b2a292837363534333231303f3e3d3c3b3a3938000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000000000000",
        "74aaa2510b5c66ea8a708bdea257192d9c9bc48d53c7f7e87a51bffd24d5d9e6ea0b4b2070fb3a3d211208e3f7bcb1a24dec971cecbb62faf1cd142745f8f4ee",
        1
      },
      {
        "000000018736a85f01b0a31dd67dcbe79b220b9b9d93e5897bb8975766b47652443b1927b715da2203c2ca1d0d22b820a965ad9cf23bb0bd57a16fd56ed7a9c5dc67af0587868584838281808f8e8d8c8b8a898897969594939291909f9e9d9c9b9a9998a7a6a5a4a3a2a1a0afaeadacabaaa9a8b7b6b5b4b3b2b1b0bfbebdbcbbbab9b8c7c6c5c4c3c2c1c0cfcecdcccbcac9c8d7d6d5d4d3d2d1d0dfdedddcdbdad9d8e7e6e5e4e3e2e1e0efeeedecebeae9e8f7f6f5f4f3f2f1f000fefdfcfbfaf9f8000000000000017f000000000000000001",
        "f0eea67995b5e71bd02c40edf19aadd131475949119813de4ea3072f8865bb1978037a72d2bb446d3deead8310b5fba548088c5a99689c51c6632f660a2a2a45",
        1
      },
      {
        "000000056a09e667f2bd8948bb67ae8584caa73b3c6ef372fe94f82ba54ff53a5f1d36f1510e527fade682d19b05688c2b3e6c1f1f83d9abfb41bd6b5be0cd19137e217907060504030201000f0e0d0c0b0a090817161514131211101f1e1d1c1b1a191827262524232221202f2e2d2c2b2a292837363534333231303f3e3d3c3b3a3938000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000000000001",
        "f863686fe221e37fee57b5eb7a41266fe765d26d337fa9fe329af1ae378bae9bd61f06dd8ccceec229349cd341b19df399488a9ab05aec7ba3442268fd039ba4",
        5
      },
      {
        "000000056a09e667f2bd8948bb67ae8584caa73b3c6ef372fe94f82ba54ff53a5f1d36f1510e527fade682d19b05688c2b3e6c1f1f83d9abfb41bd6b5be0cd19137e217907060504030201000f0e0d0c0b0a090817161514131211101f1e1d1c1b1a191827262524232221202f2e2d2c2b2a292837363534333231303f3e3d3c3b3a3938000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000000000000",
        "3a6c5ac8d6606290708c08540cb5c425f72014c8b50c280b817a6ffb1b8b405f2001caca125182b562b8a407a249ca6e9755b62e4324caaf4578a660d8dd4876",
        5
      },
      {
        "000000052a3c65f1dbd604cbc713d5ce8c0e5e6c3b22c76bdf73716e55525ba247a930f9e240763f1c01d22c514aa14d3f18722715405fbe7ecde36c77e565078839b7c987868584838281808f8e8d8c8b8a898897969594939291909f9e9d9c9b9a9998a7a6a5a4a3a2a1a0afaeadacabaaa9a8b7b6b5b4b3b2b1b0bfbebdbcbbbab9b8c7c6c5c4c3c2c1c0cfcecdcccbcac9c8d7d6d5d4d3d2d1d0dfdedddcdbdad9d8e7e6e5e4e3e2e1e0efeeedecebeae9e8f7f6f5f4f3f2f1f000fefdfcfbfaf9f8000000000000017f000000000000000001",
        "7e7acc657731d1dfb4229114f545597b7fa4b3140217b470e86a8c27ab0b1ce8c103e219a3a2f77fa495b8c706b8016facf32b1cb5d83f2ae8d3248892d5d74c",
        5
      },
      {
        "0000000cff3e48ac606d27242b6d0f03be548f10ff2d7f8bccfba4b872deac9005e263ebe6d620e38bba2bf133164736804d08f5dbf004f0c01f6df4bc02b6384c348cde00000000030201000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000084000000000000000001",
        "7180f3083d5aaabe0569cd951d62cf431dc9f9ff9eb4d014a5ef0eec4192b524ba8b0407d49601f648b0bc8e8246218d6d4fbb56fd42888dacb8aa4d4b9ce1f8",
        12
      },
      {
        "0000000cff3e48ac606d27242b6d0f03be548f10ff2d7f8bccfba4b872deac9005e263ebe6d620e38bba2bf133164736804d08f5dbf004f0c01f6df4bc02b6384c348cde07060504030201000f0e0d0c0b0a0908000000141312111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000095000000000000000001",
        "099cb773e2cc379a8876af0e51773691d22f53d314339be845292a02de394c76ddf87a51130d71b51cec3be7246631c03620302852f17de6dd18d2b40cab30f3",
        12
      },
      {
        "0000000c6a09e667f2bd8948bb67ae8584caa73b3c6ef372fe94f82ba54ff53a5f1d36f1510e527fade682d19b05688c2b3e6c1f1f83d9abfb41bd6b5be0cd19137e217907060504030201000f0e0d0c0b0a090817161514131211101f1e1d1c1b1a191827262524232221202f2e2d2c2b2a292837363534333231303f3e3d3c3b3a3938000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000000000000",
        "ff3e48ac606d27242b6d0f03be548f10ff2d7f8bccfba4b872deac9005e263ebe6d620e38bba2bf133164736804d08f5dbf004f0c01f6df4bc02b6384c348cde",
        12
      },
      {
        "0000000cff3e48ac606d27242b6d0f03be548f10ff2d7f8bccfba4b872deac9005e263ebe6d620e38bba2bf133164736804d08f5dbf004f0c01f6df4bc02b6384c348cde07060504030201000f0e0d0c0b0a0908171615141312111000000000001a1918000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009b000000000000000001",
        "54777d6e31afa286ac6453272e941b20d7d85bab6289ea12f9c8fffbc56d27fb67df67484eae8ca22709162425b7d980b5e078605bda55c81dcab91ce391aa54",
        12
      },
      {
        "0000000cff3e48ac606d27242b6d0f03be548f10ff2d7f8bccfba4b872deac9005e263ebe6d620e38bba2bf133164736804d08f5dbf004f0c01f6df4bc02b6384c348cde07060504030201000f0e0d0c0b0a09081716151413121110000000001b1a1918000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009c000000000000000001",
        "052780a0caf0bd10793f8aaf9b3606e7a77506a8030a2cd764515ea4e30bb0bb6d6fb5ef88eed1246577e2e65a5477578930fc93e4f5a8c355eedfa133896315",
        12
      },
      {
        "0000000cff3e48ac606d27242b6d0f03be548f10ff2d7f8bccfba4b872deac9005e263ebe6d620e38bba2bf133164736804d08f5dbf004f0c01f6df4bc02b6384c348cde07060504030201000f0e0d0c0b0a090817161514131211101f1e1d1c1b1a191827262524232221202f2e2d2c2b2a2928000000000000313000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000b2000000000000000001",
        "fe47a4b9e2a1e7d01080ffe977223ee8aa7afa12ae75f3c26aa2687831a6a58c32cfc1fb690b7a3601630637eb345da575ba0e2310213d6f7cf5ac546fa52840",
        12
      },
      {
        "0000000cff3e48ac606d27242b6d0f03be548f10ff2d7f8bccfba4b872deac9005e263ebe6d620e38bba2bf133164736804d08f5dbf004f0c01f6df4bc02b6384c348cde07060504030201000f0e0d0c0b0a090817161514131211101f1e1d1c1b1a191827262524232221202f2e2d2c2b2a292837363534333231303f3e3d3c3b3a393847464544434241404f4e4d4c4b4a494857565554535251505f5e5d5c5b5a595867666564636261606f6e6d6c6b6a696877767574737271707f7e7d7c7b7a79780000000000000100000000000000000000",
        "b02406865b80cde9fec1e321559891be456158a6d71cf0eac1eb845c70d4a3d8948e82cc975dcbd6984780bab7e236382fb585df5bc4b043cb3e866544413d92",
        12
      },
      {
        "0000000cb02406865b80cde9fec1e321559891be456158a6d71cf0eac1eb845c70d4a3d8948e82cc975dcbd6984780bab7e236382fb585df5bc4b043cb3e866544413d9287868584838281808f8e8d8c8b8a898897969594939291909f9e9d9c9b9a9998a7a6a5a4a3a2a1a0afaeadacabaaa9a8b7b6b5b4b3b2b1b0bfbebdbcbbbab9b8c7c6c5c4c3c2c1c0cfcecdcccbcac9c8d7d6d5d4d3d2d1d0dfdedddcdbdad9d8e7e6e5e4e3e2e1e000000000000000e8000000000000000000000000000000000000000000000169000000000000000001",
        "ca769859d42c76d4db449924feb8b27536b9da1f74ce7ad2eb0f4625e4c6cb160e1838ccade7d451567f4a02897cc47feae4fd8d87db1a19fe0e61a2f52322d6",
        12
      },
      {
        "0000000cb02406865b80cde9fec1e321559891be456158a6d71cf0eac1eb845c70d4a3d8948e82cc975dcbd6984780bab7e236382fb585df5bc4b043cb3e866544413d9287868584838281808f8e8d8c8b8a898897969594939291909f9e9d9c9b9a9998a7a6a5a4a3a2a1a0afaeadacabaaa9a8b7b6b5b4b3b2b1b0bfbebdbcbbbab9b8c7c6c5c4c3c2c1c0cfcecdcccbcac9c8d7d6d5d4d3d2d1d0dfdedddcdbdad9d8e7e6e5e4e3e2e1e0efeeedecebeae9e800000000f3f2f1f000000000000000000000000000000174000000000000000001",
        "8187dd8f261496b3d5b489bffe2c5e5134e626c210ab2b406c0dfb00e09a6b4eea800ec83e2fcb79168969f8d28019eb51653672749f2ebd37a823cad39c6416",
        12
      },
      {
        "0000000cb02406865b80cde9fec1e321559891be456158a6d71cf0eac1eb845c70d4a3d8948e82cc975dcbd6984780bab7e236382fb585df5bc4b043cb3e866544413d9287868584838281808f8e8d8c8b8a898897969594939291909f9e9d9c9b9a9998a7a6a5a4a3a2a1a0afaeadacabaaa9a8b7b6b5b4b3b2b1b0bfbebdbcbbbab9b8c7c6c5c4c3c2c1c0cfcecdcccbcac9c8d7d6d5d4d3d2d1d0dfdedddcdbdad9d8e7e6e5e4e3e2e1e0efeeedecebeae9e8f7f6f5f4f3f2f1f000000000000000f80000000000000179000000000000000001",
        "f5b5dbc5e76321b3ef75c8ead211dc1f7e0a0999767ecbbb5daf9507d5a8f87fcdf83e5498ffd974872785043dc19af8567417c800efe05637758ee39fd5e161",
        12
      },
      {
        "0000000cff3e48ac606d27242b6d0f03be548f10ff2d7f8bccfba4b872deac9005e263ebe6d620e38bba2bf133164736804d08f5dbf004f0c01f6df4bc02b6384c348cde07060504030201000f0e0d0c0b0a090817161514131211101f1e1d1c1b1a191827262524232221202f2e2d2c2b2a292837363534333231303f3e3d3c3b3a393847464544434241404f4e4d4c4b4a494857565554535251505f5e5d5c5b5a595867666564636261606f6e6d6c6b6a696877767574737271707f7e7d7c7b7a79780000000000000100000000000000000000",
        "b02406865b80cde9fec1e321559891be456158a6d71cf0eac1eb845c70d4a3d8948e82cc975dcbd6984780bab7e236382fb585df5bc4b043cb3e866544413d92",
        12
      },
      {
        "0000000cb02406865b80cde9fec1e321559891be456158a6d71cf0eac1eb845c70d4a3d8948e82cc975dcbd6984780bab7e236382fb585df5bc4b043cb3e866544413d9287868584838281808f8e8d8c8b8a898897969594939291909f9e9d9c9b9a9998a7a6a5a4a3a2a1a0afaeadacabaaa9a8b7b6b5b4b3b2b1b0bfbebdbcbbbab9b8c7c6c5c4c3c2c1c0cfcecdcccbcac9c8d7d6d5d4d3d2d1d0dfdedddcdbdad9d8e7e6e5e4e3e2e1e0efeeedecebeae9e8f7f6f5f4f3f2f1f000fefdfcfbfaf9f8000000000000017f000000000000000001",
        "ccfc282ed60927145b46f8d0fa97afd07010c51d20821e9748923ea42a37a0fa0609a13be7c1e14b6e10a4b63d85d1d56d3d370d80f97b0a61a4f22ed6462dee",
        12
      },
      {
        "0000000cb02406865b80cde9fec1e321559891be456158a6d71cf0eac1eb845c70d4a3d8948e82cc975dcbd6984780bab7e236382fb585df5bc4b043cb3e866544413d9287868584838281808f8e8d8c8b8a898897969594939291909f9e9d9c9b9a9998a7a6a5a4a3a2a1a0afaeadacabaaa9a8b7b6b5b4b3b2b1b0bfbebdbcbbbab9b8c7c6c5c4c3c2c1c0cfcecdcccbcac9c8d7d6d5d4d3d2d1d0dfdedddcdbdad9d8e7e6e5e4e3e2e1e0efeeedecebeae9e8f7f6f5f4f3f2f1f000fefdfcfbfaf9f8000000000000017f000000000000000010",
        "ccfc282ed60927145b46f8d0fa97afd07010c51d20821e9748923ea42a37a0fa0609a13be7c1e14b6e10a4b63d85d1d56d3d370d80f97b0a61a4f22ed6462dee",
        12
      },
      {
        "0000000cb02406865b80cde9fec1e321559891be456158a6d71cf0eac1eb845c70d4a3d8948e82cc975dcbd6984780bab7e236382fb585df5bc4b043cb3e866544413d9287868584838281808f8e8d8c8b8a898897969594939291909f9e9d9c9b9a9998a7a6a5a4a3a2a1a0afaeadacabaaa9a8b7b6b5b4b3b2b1b0bfbebdbcbbbab9b8c7c6c5c4c3c2c1c0cfcecdcccbcac9c8d7d6d5d4d3d2d1d0dfdedddcdbdad9d8e7e6e5e4e3e2e1e0efeeedecebeae9e8f7f6f5f4f3f2f1f000fefdfcfbfaf9f8000000000000017f000000000000000011",
        "ccfc282ed60927145b46f8d0fa97afd07010c51d20821e9748923ea42a37a0fa0609a13be7c1e14b6e10a4b63d85d1d56d3d370d80f97b0a61a4f22ed6462dee",
        12
      },
    };
  }

  @Parameterized.Parameter public String input;

  @Parameterized.Parameter(1)
  public String expectedResult;

  @Parameterized.Parameter(2)
  public long expectedGasUsed;

  @Test
  public void shouldRunFCompression() {
    final BytesValue input = BytesValue.fromHexString(this.input);
    final BytesValue expectedComputation =
        expectedResult == null ? BytesValue.EMPTY : BytesValue.fromHexString(expectedResult);
    assertThat(contract.compute(input, messageFrame)).isEqualTo(expectedComputation);
    assertThat(contract.gasRequirement(input)).isEqualTo(Gas.of(expectedGasUsed));
  }
}
