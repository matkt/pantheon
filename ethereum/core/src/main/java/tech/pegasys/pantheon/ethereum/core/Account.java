/*
 * Copyright 2018 ConsenSys AG.
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
package tech.pegasys.pantheon.ethereum.core;

import tech.pegasys.pantheon.util.bytes.Bytes32;
import tech.pegasys.pantheon.util.bytes.BytesValue;
import tech.pegasys.pantheon.util.uint.UInt256;

import java.util.NavigableMap;

/**
 * A world state account.
 *
 * <p>An account has four properties associated with it:
 *
 * <ul>
 *   <li><b>Nonce:</b> the number of transactions sent (and committed) from the account.
 *   <li><b>Balance:</b> the amount of Wei (10^18 Ether) owned by the account.
 *   <li><b>Storage:</b> a key-value mapping between 256-bit integer values. Note that formally, a
 *       key always has an associated value in that mapping, with 0 being the default (and thus, in
 *       practice, only non-zero mappings are stored and setting a key to the value 0 is akin to
 *       "removing" that key).
 *   <li><b>Code:</b> arbitrary-length sequence of bytes that corresponds to EVM bytecode.
 *   <li><b>Version:</b> the version of the EVM bytecode.
 * </ul>
 */
public interface Account {

  long DEFAULT_NONCE = 0L;
  Wei DEFAULT_BALANCE = Wei.ZERO;
  int DEFAULT_VERSION = 0;

  /**
   * The Keccak-256 hash of the account address.
   *
   * <p>Note that the world state does not store account addresses, only their hashes, and so this
   * is how account are truly identified. So while accounts can be queried by their address (through
   * first computing their hash), one cannot infer the address from an account simply from the world
   * state.
   *
   * @return the Keccak-256 hash of the account address.
   */
  default Hash getAddressHash() {
    return Hash.hash(getAddress());
  }

  /**
   * The account address.
   *
   * @return the account address
   */
  Address getAddress();

  /**
   * The account nonce, that is the number of transactions sent from that account.
   *
   * @return the account nonce.
   */
  long getNonce();

  /**
   * The available balance of that account.
   *
   * @return the balance, in Wei, of the account.
   */
  Wei getBalance();

  /**
   * The EVM bytecode associated with this account.
   *
   * @return the account code (which can be empty).
   */
  BytesValue getCode();

  /**
   * The hash of the EVM bytecode associated with this account.
   *
   * @return the hash of the account code (which may be {@link Hash#EMPTY}).
   */
  Hash getCodeHash();

  /**
   * Whether the account has (non empty) EVM bytecode associated to it.
   *
   * <p>This is functionally equivalent to {@code !code().isEmpty()}, though could be implemented
   * more efficiently.
   *
   * @return Whether the account has EVM bytecode associated to it.
   */
  default boolean hasCode() {
    return !getCode().isEmpty();
  }

  /**
   * The version of the EVM bytecode associated with this account.
   *
   * @return the version of the account code. Default is zero.
   */
  int getVersion();

  /**
   * Retrieves a value in the account storage given its key.
   *
   * @param key the key to retrieve in the account storage.
   * @return the value associated to {@code key} in the account storage. Note that this is never
   *     {@code null}, but 0 acts as a default value.
   */
  UInt256 getStorageValue(UInt256 key);

  /**
   * Retrieves the original value from before the current transaction in the account storage given
   * its key.
   *
   * @param key the key to retrieve in the account storage.
   * @return the original value associated to {@code key} in the account storage. Note that this is
   *     never {@code null}, but 0 acts as a default value.
   */
  UInt256 getOriginalStorageValue(UInt256 key);

  /**
   * Whether the account is "empty".
   *
   * <p>An account is defined as empty if:
   *
   * <ul>
   *   <li>its nonce is 0;
   *   <li>its balance is 0;
   *   <li>its associated code is empty (the zero-length byte sequence).
   * </ul>
   *
   * @return {@code true} if the account is empty with regard to the definition above, {@code false}
   *     otherwise.
   */
  default boolean isEmpty() {
    return getNonce() == 0 && getBalance().isZero() && !hasCode();
  }

  /**
   * Retrieve up to {@code limit} storage entries beginning from the first entry with hash equal to
   * or greater than {@code startKeyHash}.
   *
   * @param startKeyHash the first key hash to return.
   * @param limit the maximum number of entries to return.
   * @return the requested storage entries as a map of key hash to value.
   */
  NavigableMap<Bytes32, UInt256> storageEntriesFrom(Bytes32 startKeyHash, int limit);
}
